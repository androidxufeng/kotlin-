package com.xufeng.mvpkotlin.adapter

/**
 * Ver 1.0, 18-9-25, xufeng, Create file
 */
interface MultipleType<in T> {

    fun getLayoutId(item: T, position: Int): Int
}