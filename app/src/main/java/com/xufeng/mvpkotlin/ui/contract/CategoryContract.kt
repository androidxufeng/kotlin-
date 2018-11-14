package com.xufeng.mvpkotlin.ui.contract

import com.xufeng.mvpkotlin.base.IBaseView
import com.xufeng.mvpkotlin.base.IPresenter
import com.xufeng.mvpkotlin.bean.CategoryBean

/**
 * Ver 1.0, 18-9-26, xufeng, Create file
 */
interface CategoryContract {

    interface View : IBaseView {
        /**
         * 显示分类的信息
         */
        fun showCategory(categoryList: ArrayList<CategoryBean>)

        /**
         * 显示错误信息
         */
        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        /**
         * 获取分类的信息
         */
        fun getCategoryData()
    }
}