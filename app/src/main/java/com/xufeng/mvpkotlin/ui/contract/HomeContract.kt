package com.xufeng.mvpkotlin.ui.contract

import com.xufeng.mvpkotlin.base.BaseView
import com.xufeng.mvpkotlin.base.IPresenter
import com.xufeng.mvpkotlin.bean.HomeBean

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
interface HomeContract {

    interface View : BaseView {

        fun showError(msg: String)

        fun showHomeData(data: HomeBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestHomeData(num: Int)
    }
}