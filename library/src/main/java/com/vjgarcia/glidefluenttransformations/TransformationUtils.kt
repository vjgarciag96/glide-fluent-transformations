package com.vjgarcia.glidefluenttransformations

import android.graphics.Bitmap

object TransformationUtils {

    fun getNonNullConfig(bitmap: Bitmap): Bitmap.Config {
        if (bitmap.config == null) {
            return Bitmap.Config.ARGB_8888
        }

        return bitmap.config
    }
}