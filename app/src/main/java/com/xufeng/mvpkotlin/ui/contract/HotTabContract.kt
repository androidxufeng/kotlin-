package com.xufeng.mvpkotlin.ui.contract

import com.xufeng.mvpkotlin.base.IBaseView
import com.xufeng.mvpkotlin.base.IPresenter
import com.xufeng.mvpkotlin.bean.TabInfoBean

/**
 * Ver 1.0, 18-9-27, xufeng, Create file
 */
interface HotTabContract {

    interface View : IBaseView {
        fun showError(msg: String)

        fun setTabInfo(tabInfoBean: TabInfoBean)
    }

    interface Presenter : IPresenter<View> {
        fun getTabInfo()
    }
}