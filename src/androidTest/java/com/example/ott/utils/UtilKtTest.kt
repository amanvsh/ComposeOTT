package com.example.ott.utils

import org.junit.Assert.*
import android.content.Context
import androidx.test.core.app.ApplicationProvider

import org.junit.Test

class UtilKtTest {

    @Test
    fun getImageDrawableResourceID() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        val imageName = "poster1.jpg"

        val drawableResourceId = getImageDrawable(context, imageName)

        val expectedResourceId = context.resources.getIdentifier(
            imageName.removeSuffix(".jpg"),
            "drawable",
            context.packageName
        )

        assertEquals(expectedResourceId, drawableResourceId)
    }
}