package com.vjgarcia.glidefluenttransformations

import android.graphics.*
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class ColorMaskTransformation internal constructor(private val argbColor: Int) : BitmapTransformation() {

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val bitmap = pool.get(
            toTransform.width,
            toTransform.height,
            TransformationUtils.getNonNullConfig(toTransform)
        ).apply { setHasAlpha(true) }

        val paint = Paint()
        paint.isAntiAlias = true
        paint.colorFilter = PorterDuffColorFilter(argbColor, PorterDuff.Mode.SRC_ATOP)

        Canvas(bitmap).drawBitmap(toTransform, 0f, 0f, paint)

        return bitmap
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update((ID + argbColor).toByteArray(charset = Key.CHARSET))
    }

    private companion object {
        const val ID = "com.vjgarcia.glidefluenttransformations.ColorMaskTransformation"
    }
}

// Fluent API

fun <T> RequestBuilder<T>.colorMask(argbColor: Int): RequestBuilder<T> = apply {
    transform(ColorMaskTransformation(argbColor))
}