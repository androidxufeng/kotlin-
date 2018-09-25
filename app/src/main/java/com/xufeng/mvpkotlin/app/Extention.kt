package com.xufeng.mvpkotlin.app

import android.view.View

/**
 * Ver 1.0, 18-9-21, xufeng, Create file
 * 提供扩展方法
 */

fun View.dp2px(dipValue: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (dipValue * scale + 0.5f).toInt()
}

fun View.px2dp(pxValue: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}