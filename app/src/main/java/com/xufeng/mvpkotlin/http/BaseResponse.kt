package com.xufeng.mvpkotlin.http

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
class BaseResponse<T>(val code: Int,
                      val msg: String,
                      val data: T)