package com.vjgarcia.glidefluenttransformations

import android.app.Application
import android.graphics.Bitmap
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
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [28])
class TopCropTest {

    private val context: Application = ApplicationProvider.getApplicationContext()
    private val bitmapPool: BitmapPool = mockk()
    private val resource: Resource<Bitmap> = mockk()

    private val sut = CropTransformation(cropType = CropTransformation.CropType.TOP)

    @Before
    fun setUp() {
        Glide.init(context, GlideBuilder().setBitmapPool(bitmapPool))
    }

    @After
    fun tearDown() {
        Glide.tearDown()
    }

    @Test
    fun `Returns given resource if matches size exactly`() {
        givenResourceBitmap(width = Fixture.anyWidth, height = Fixture.anyHeight)

        val result = sut.transform(context, resource, Fixture.anyWidth, Fixture.anyHeight)

        assertThat(result, `is`(resource))
    }

    @Test
    fun `Doesn't recycle given resource if matches size exactly`() {
        givenResourceBitmap(width = Fixture.anyWidth, height = Fixture.anyHeight)

        sut.transform(context, resource, Fixture.anyWidth, Fixture.anyHeight)

        verify(exactly = 0) { resource.recycle() }
    }

    @Test
    fun `Doesn't recycle given resource`() {
        givenResourceBitmap(width = Fixture.anyOtherWidth, height = Fixture.anyOtherHeight)
        givenAMockedPool()

        sut.transform(context, resource, Fixture.anyWidth, Fixture.anyHeight)

        verify(exactly = 0) { resource.recycle() }
    }

    @Test
    fun `Asks bitmap pool for argb8888 if in config is null`() {
        givenResourceBitmap(bitmapConfig = null)
        givenAMockedPool()

        sut.transform(context, resource, Fixture.anyOtherWidth, Fixture.anyOtherHeight)

        verify { bitmapPool.get(any(), any(), eq(Bitmap.Config.ARGB_8888)) }
        verify(exactly = 0) { bitmapPool.get(any(), any(), isNull()) }
    }

    @Test
    fun `Returns bitmap with exactly given dimensions if bitmap is larger than target`() {
        givenResourceBitmap(width = Fixture.anyLargeWidth, height = Fixture.anyLargeHeight)
        givenAMockedPool(width = Fixture.anyWidth, height = Fixture.anyHeight)

        val result = sut.transform(context, resource, Fixture.anyWidth, Fixture.anyHeight)

        val transformedBitmap = result.get()
        assertThat(transformedBitmap.height, `is`(Fixture.anyHeight))
        assertThat(transformedBitmap.width, `is`(Fixture.anyWidth))
    }

    @Test
    fun `Returns bitmap with exactly given dimensions if bitmap is smaller than target`() {
        givenResourceBitmap(width = Fixture.anySmallWidth, height = Fixture.anySmallHeight)
        givenAMockedPool(width = Fixture.anyWidth, height = Fixture.anyHeight)

        val result = sut.transform(context, resource, Fixture.anyWidth, Fixture.anyHeight)

        val transformedBitmap = result.get()
        assertThat(transformedBitmap.height, `is`(Fixture.anyHeight))
        assertThat(transformedBitmap.width, `is`(Fixture.anyWidth))
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
        const val anyWidth = 100
        const val anyHeight = 100
        const val anyOtherWidth = 300
        const val anyOtherHeight = 200
        const val anyLargeWidth = 800
        const val anyLargeHeight = 900
        const val anySmallWidth = 20
        const val anySmallHeight = 20
    }
}