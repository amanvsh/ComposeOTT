package com.example.ott.utils

import android.content.Context
import android.content.res.Configuration


fun getImageDrawable(context: Context, image: String): Int {
    return context.resources.getIdentifier(
        image.removeSuffix(".jpg"),
        "drawable",
        context.packageName
    )
}

fun getScreenOrientation(current: Configuration): Int {
    return if (current.orientation == 1) {
        3 // Set 3 columns for portrait mode
    } else {
        5 // Set 5 columns for landscape mode
    }
}


