package me.hamedsj.gridchallenge.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun Dp.toPx(): Float {
    return with(LocalDensity.current) { toPx() }
}

@Composable
fun Float.toDp(): Dp {
    return with(LocalDensity.current) { toDp() }
}

fun Int.toPx(context: Context): Int = (this * context.resources.displayMetrics.density).toInt()

fun Int.toDp(context: Context): Int = (this / context.resources.displayMetrics.density).toInt()