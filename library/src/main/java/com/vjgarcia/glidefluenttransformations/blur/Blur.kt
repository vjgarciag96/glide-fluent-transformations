package com.vjgarcia.glidefluenttransformations.blur

import android.graphics.Bitmap

interface Blur {
    fun run(bitmap: Bitmap, radius: Int): Bitmap
}