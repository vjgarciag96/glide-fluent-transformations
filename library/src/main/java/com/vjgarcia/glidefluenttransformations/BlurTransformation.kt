package com.vjgarcia.glidefluenttransformations

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.vjgarcia.glidefluenttransformations.blur.Blur
import com.vjgarcia.glidefluenttransformations.blur.BoxBlur
import com.vjgarcia.glidefluenttransformations.blur.GaussianFastBlur
import com.vjgarcia.glidefluenttransformations.blur.SuperFastBlur
import java.security.MessageDigest

class BlurTransformation internal constructor(
    private val downSampling: Int,
    private val radius: Int,
    private val blurAlgorithm: BlurAlgorithm
) : BitmapTransformation() {

    enum class BlurAlgorithm {
        BOX,
        GAUSSIAN_FAST,
        SUPER_FAST
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val bitmap = pool.get(
            toTransform.width,
            toTransform.height,
            TransformationUtils.getNonNullConfig(toTransform)
        ).apply { setHasAlpha(true) }

        val samplingScale = 1F / downSampling

        val canvas = Canvas(bitmap).apply { scale(samplingScale, samplingScale) }
        val paint = Paint()
        paint.flags = Paint.FILTER_BITMAP_FLAG
        canvas.drawBitmap(toTransform, 0F, 0F, paint)

        return getBlur(blurAlgorithm).run(bitmap, radius)
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update((ID + downSampling + radius).toByteArray(charset = Key.CHARSET))
    }

    private fun getBlur(blurAlgorithm: BlurAlgorithm): Blur =
        when (blurAlgorithm) {
            BlurAlgorithm.BOX -> BoxBlur()
            BlurAlgorithm.GAUSSIAN_FAST -> GaussianFastBlur()
            BlurAlgorithm.SUPER_FAST -> SuperFastBlur()
        }

    private companion object {
        const val ID = "com.vjgarcia.glidefluenttransformations.BlurTransformation"
    }
}

// Fluent API

private const val DEFAULT_DOWN_SAMPLING = 1
private val DEFAULT_BLUR_ALGORITHM = BlurTransformation.BlurAlgorithm.SUPER_FAST

fun <T> RequestBuilder<T>.blur(
    downSampling: Int = DEFAULT_DOWN_SAMPLING,
    radius: Int,
    blurAlgorithm: BlurTransformation.BlurAlgorithm = DEFAULT_BLUR_ALGORITHM
): RequestBuilder<T> = apply {
    transform(BlurTransformation(downSampling, radius, blurAlgorithm))
}
