package com.xufeng.mvpkotlin.http.exception

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */

class ApiException : RuntimeException {
    private var code: Int? = null

    constructor(throwable: Throwable, code: Int) : super(throwable) {
        this.code = code
    }

    constructor(message: String) : super(Throwable(message))
}
