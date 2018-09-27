package com.xufeng.mvpkotlin.ui.contract

import com.xufeng.mvpkotlin.base.IBaseView
import com.xufeng.mvpkotlin.base.IPresenter
import com.xufeng.mvpkotlin.bean.HomeBean

/**
 * Ver 1.0, 18-9-26, xufeng, Create file
 */
interface CategoryDetailContract {

    interface View : IBaseView {
        /**
         * 显示分类的信息
         */
        fun showCategoryDetail(itemList: ArrayList<HomeBean.Issue.Item>)

        /**
         * 显示错误信息
         */
        fun showError(errorMsg: String)
    }

    interface Presenter : IPresenter<View> {
        /**
         * 获取分类的信息
         */
        fun getCategoryDetailList(id: Long)

        fun loadMoreData()
    }
}