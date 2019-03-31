package com.vjgarcia.transformationssample

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.vjgarcia.glidefluenttransformations.*

class DemoActivity : AppCompatActivity() {

    private lateinit var topCropImage: ImageView
    private lateinit var centerCropImage: ImageView
    private lateinit var bottomCropImage: ImageView
    private lateinit var rightCropImage: ImageView
    private lateinit var anotherCenterCropImage: ImageView
    private lateinit var leftCropImage: ImageView

    private lateinit var yellowColorFilterImage: ImageView
    private lateinit var redColorFilterImage: ImageView
    private lateinit var blueColorFilterImage: ImageView

    private lateinit var gaussianFastBlurImage: ImageView
    private lateinit var boxBlurImage: ImageView
    private lateinit var superFastBlurImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        topCropImage = findViewById(R.id.top_crop_image)
        centerCropImage = findViewById(R.id.center_crop_image)
        bottomCropImage = findViewById(R.id.bottom_crop_image)
        leftCropImage = findViewById(R.id.left_crop_image)
        anotherCenterCropImage = findViewById(R.id.another_center_crop_image)
        rightCropImage = findViewById(R.id.right_crop_image)

        yellowColorFilterImage = findViewById(R.id.yellow_color_filter_image)
        redColorFilterImage = findViewById(R.id.red_color_filter_image)
        blueColorFilterImage = findViewById(R.id.blue_color_filter_image)

        gaussianFastBlurImage = findViewById(R.id.gaussian_fast_blur_demo_image)
        boxBlurImage = findViewById(R.id.box_blur_demo_image)
        superFastBlurImage = findViewById(R.id.super_fast_blur_demo_image)

        setImages()
    }

    private fun setImages() {
        val imageUrl =
            "https://images-na.ssl-images-amazon.com/images/I/41HXUK8edZL.png"
        val anotherImageUrl =
            "https://cdn-images-1.medium.com/max/2600/1*0ubYRV_WNR9iYrzUAVINHw.jpeg"

        Glide.with(this)
            .load(Uri.parse(imageUrl))
            .topCrop()
            .into(topCropImage)

        Glide.with(this)
            .load(Uri.parse(imageUrl))
            .centerCrop()
            .into(centerCropImage)

        Glide.with(this)
            .load(Uri.parse(imageUrl))
            .bottomCrop()
            .into(bottomCropImage)

        Glide.with(this)
            .load(Uri.parse(anotherImageUrl))
            .leftCrop()
            .into(leftCropImage)

        Glide.with(this)
            .load(Uri.parse(anotherImageUrl))
            .centerCrop()
            .into(anotherCenterCropImage)

        Glide.with(this)
            .load(Uri.parse(anotherImageUrl))
            .rightCrop()
            .into(rightCropImage)

        Glide.with(this)
            .load(Uri.parse(anotherImageUrl))
            .centerCrop()
            .colorMask(Color.argb(100, 255, 255, 0))
            .into(yellowColorFilterImage)

        Glide.with(this)
            .load(Uri.parse(anotherImageUrl))
            .centerCrop()
            .colorMask(Color.argb(100, 255, 0, 0))
            .into(redColorFilterImage)

        Glide.with(this)
            .load(Uri.parse(anotherImageUrl))
            .centerCrop()
            .colorMask(Color.argb(100, 0, 0, 255))
            .into(blueColorFilterImage)

        Glide.with(this)
            .load(Uri.parse(anotherImageUrl))
            .blur(radius = 25, blurAlgorithm = BlurTransformation.BlurAlgorithm.BOX)
            .into(boxBlurImage)

        Glide.with(this)
            .load(Uri.parse(anotherImageUrl))
            .blur(radius = 25, blurAlgorithm = BlurTransformation.BlurAlgorithm.GAUSSIAN_FAST)
            .into(gaussianFastBlurImage)

        Glide.with(this)
            .load(Uri.parse(anotherImageUrl))
            .blur(radius = 25, blurAlgorithm = BlurTransformation.BlurAlgorithm.SUPER_FAST)
            .into(superFastBlurImage)
    }
}

