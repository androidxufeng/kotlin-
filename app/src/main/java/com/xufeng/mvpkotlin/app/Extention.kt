package com.xufeng.mvpkotlin.app

import android.content.Context
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

fun durationFormat(duration: Long?): String {
    val minute = duration!! / 60
    val second = duration % 60
    return if (minute <= 9) {
        if (second <= 9) {
            "0$minute' 0$second''"
        } else {
            "0$minute' $second''"
        }
    } else {
        if (second <= 9) {
            "$minute' 0$second''"
        } else {
            "$minute' $second''"
        }
    }
}

/**
 * 数据流量格式化
 */
fun Context.dataFormat(total: Long): String {
    var result = ""
    var speedReal = 0
    speedReal = (total / (1024)).toInt()
    if (speedReal < 512) {
        result = speedReal.toString() + " KB"
    } else {
        val mSpeed = speedReal / 1024.0
        result = (Math.round(mSpeed * 100) / 100.0).toString() + " MB"
    }
    return result
}
