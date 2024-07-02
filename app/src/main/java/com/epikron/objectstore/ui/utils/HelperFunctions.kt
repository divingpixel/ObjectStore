@file:Suppress("unused")

package com.epikron.objectstore.ui.utils

import android.content.res.Resources
import android.util.DisplayMetrics
import androidx.compose.ui.unit.Dp

fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.pxToDp(): Dp {
    val displayDensity = Resources.getSystem().displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT
    return Dp(this / displayDensity)
}
