package com.vjgarcia.glidefluenttransformations

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Color
import androidx.test.core.app.ApplicationProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class ColorMaskTransformationTest : GlideTest() {

    private val context: Application = ApplicationProvider.getApplicationContext()
    private val bitmapPool: BitmapPool = mockk()
    private val resource: Resource<Bitmap> = mockk()

    private val sut = ColorMaskTransformation(anyArgbColor)

    @Before
    fun setUp() {
        Glide.init(context, GlideBuilder().setBitmapPool(bitmapPool))
    }

    @After
    fun tearDown() {
        Glide.tearDown()
    }

    @Test
    fun `Doesn't recycle given resource`() {
        givenResourceBitmap(width = Fixture.anyOtherWidth, height = Fixture.anyOtherHeight)
        givenAMockedPool()

        sut.transform(context, resource, Fixture.anyWidth, Fixture.anyHeight)

        verify(exactly = 0) { resource.recycle() }
    }

    @Test
    fun `Returns bitmap with same width and height as initial`() {
        givenResourceBitmap(width = Fixture.anyWidth, height = Fixture.anyHeight)
        givenAMockedPool()

        val bitmapResource = sut.transform(context, resource, Fixture.anyOtherWidth, Fixture.anyOtherHeight)

        val bitmapResult = bitmapResource.get()
        assertThat(bitmapResult.width, `is`(Fixture.anyWidth))
        assertThat(bitmapResult.height, `is`(Fixture.anyHeight))
    }

    @Test
    fun `Asks bitmap pool for argb8888 if in config is null`() {
        givenResourceBitmap(bitmapConfig = null)
        givenAMockedPool()

        sut.transform(context, resource, Fixture.anyOtherWidth, Fixture.anyOtherHeight)

        verify { bitmapPool.get(any(), any(), eq(Bitmap.Config.ARGB_8888)) }
        verify(exactly = 0) { bitmapPool.get(any(), any(), isNull()) }
    }

    private fun givenResourceBitmap(
        width: Int = Fixture.anyWidth,
        height: Int = Fixture.anyHeight,
        bitmapConfig: Bitmap.Config? = Bitmap.Config.ARGB_8888
    ) {
        val bitmap = Bitmap.createBitmap(width, height, bitmapConfig)
        every { resource.get() } returns bitmap
    }

    private fun givenAMockedPool(width: Int = Fixture.anyWidth, height: Int = Fixture.anyHeight) {
        every { bitmapPool.get(any(), any(), any()) } returns Bitmap.createBitmap(
            width,
            height,
            Bitmap.Config.ARGB_8888
        )
    }

    private companion object Fixture {
        const val anyAlpha = 80
        val anyArgbColor = Color.argb(Fixture.anyAlpha, 255, 255, 0)
        const val anyWidth = 300
        const val anyHeight = 400
        const val anyOtherWidth = 300
        const val anyOtherHeight = 400
    }
}