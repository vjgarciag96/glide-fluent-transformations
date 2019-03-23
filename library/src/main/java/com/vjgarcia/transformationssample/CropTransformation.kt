package com.vjgarcia.glidefluenttransformations

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class CropTransformation internal constructor(private val cropType: CropType) : BitmapTransformation() {

    enum class CropType {
        TOP,
        BOTTOM,
        RIGHT,
        LEFT
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        if ((outWidth == 0 && outHeight == 0) || (outWidth == toTransform.width && outHeight == toTransform.height)) {
            return toTransform
        }

        val bitmap = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888).apply { setHasAlpha(true) }

        val scaleX = outWidth.toFloat() / toTransform.width
        val scaleY = outHeight.toFloat() / toTransform.height
        val scale = Math.max(scaleX, scaleY)

        val scaledWidth = scale * toTransform.width
        val scaledHeight = scale * toTransform.height
        val left = getStartX(scaledWidth, outWidth)
        val top = getStartY(scaledHeight, outHeight)
        val targetRect = RectF(left, top, left + scaledWidth, top + scaledHeight)

        Canvas(bitmap).apply { drawBitmap(toTransform, null, targetRect, null) }

        return bitmap
    }

    private fun getStartX(scaledWidth: Float, width: Int) = when (cropType) {
        CropType.LEFT -> 0F
        CropType.RIGHT -> (width - scaledWidth)
        else -> (width - scaledWidth) / 2
    }

    private fun getStartY(scaledHeight: Float, height: Int): Float = when (cropType) {
        CropType.TOP -> 0F
        CropType.BOTTOM -> (height - scaledHeight)
        else -> (height - scaledHeight) / 2
    }

    private companion object {
        const val ID = "com.vjgarcia.glidefluenttransformations.CropTransformation"
        val ID_BYTES = ID.toByteArray(charset = Key.CHARSET)
    }
}

// Fluent API

fun <T> RequestBuilder<T>.topCrop(): RequestBuilder<T> = apply {
    transform(CropTransformation(cropType = CropTransformation.CropType.TOP))
}

fun <T> RequestBuilder<T>.bottomCrop(): RequestBuilder<T> = apply {
    transform(CropTransformation(cropType = CropTransformation.CropType.BOTTOM))
}

fun <T> RequestBuilder<T>.rightCrop(): RequestBuilder<T> = apply {
    transform(CropTransformation(cropType = CropTransformation.CropType.RIGHT))
}

fun <T> RequestBuilder<T>.leftCrop(): RequestBuilder<T> = apply {
    transform(CropTransformation(cropType = CropTransformation.CropType.LEFT))
}
