package com.xufeng.mvpkotlin.ui.contract

import com.xufeng.mvpkotlin.base.IBaseView
import com.xufeng.mvpkotlin.base.IPresenter
import com.xufeng.mvpkotlin.bean.HomeBean

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
interface HomeContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun showHomeData(data: HomeBean)

        /**
         * 设置加载更多的数据
         */
        fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>)
    }

    interface Presenter : IPresenter<View> {
        fun requestHomeData(num: Int)

        fun loadMoreData()
    }
}