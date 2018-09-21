package com.xufeng.mvpkotlin.main

import com.xufeng.mvpkotlin.base.BaseView
import com.xufeng.mvpkotlin.base.IPresenter
import com.xufeng.mvpkotlin.bean.FuckGoods

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
interface MainContract {

    interface View : BaseView {

        fun showError(msg: String)

        fun showAndroidData(data: List<FuckGoods>)
    }

    interface Presenter : IPresenter<View> {
        fun getAndroidData(page: Int)
    }
}