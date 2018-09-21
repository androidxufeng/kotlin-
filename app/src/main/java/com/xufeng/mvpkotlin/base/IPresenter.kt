package com.xufeng.mvpkotlin.base

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
interface IPresenter<in V : BaseView> {

    fun attachView(view: V)

    fun detachView()
}