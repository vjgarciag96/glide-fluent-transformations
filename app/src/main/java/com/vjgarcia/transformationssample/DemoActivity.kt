package com.vjgarcia.transformationssample

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.vjgarcia.glidefluenttransformations.bottomCrop
import com.vjgarcia.glidefluenttransformations.leftCrop
import com.vjgarcia.glidefluenttransformations.rightCrop
import com.vjgarcia.glidefluenttransformations.topCrop

class DemoActivity : AppCompatActivity() {

    private lateinit var topCropImage: ImageView
    private lateinit var centerCropImage: ImageView
    private lateinit var bottomCropImage: ImageView
    private lateinit var rightCropImage: ImageView
    private lateinit var anotherCenterCropImage: ImageView
    private lateinit var leftCropImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        topCropImage = findViewById(R.id.top_crop_image)
        centerCropImage = findViewById(R.id.center_crop_image)
        bottomCropImage = findViewById(R.id.bottom_crop_image)
        leftCropImage = findViewById(R.id.left_crop_image)
        anotherCenterCropImage = findViewById(R.id.another_center_crop_image)
        rightCropImage = findViewById(R.id.right_crop_image)

        setImages()
    }

    private fun setImages() {
        val imageUrl =
            "https://img.buzzfeed.com/buzzfeed-static/static/enhanced/terminal01/2011/4/20/11/enhanced-buzz-32121-1303312216-6.jpg"
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
    }

}

