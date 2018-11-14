/*
 * Copyright (C), 2018, TP-LINK TECHNOLOGIES CO., LTD.
 *
 * ${FILE_NAME}
 *
 * Description
 *
 * Author xufeng
 *
 * Ver 1.0, 18-11-12, xufeng, Create file
 */

package com.xufeng.mvpkotlin.ui.contract

import com.xufeng.mvpkotlin.base.IBaseView
import com.xufeng.mvpkotlin.base.IPresenter
import com.xufeng.mvpkotlin.bean.HomeBean

interface SearchContract {

    interface View : IBaseView {
        /**
         * 设置热门关键词数据
         */
        fun setHotWordData(string: ArrayList<String>)

        /**
         * 设置搜索关键词返回的结果
         */
        fun setSearchResult(issue: HomeBean.Issue)

        /**
         * 设置空 View
         */
        fun setEmptyView()

        fun showError(errorMsg: String, errorCode: Int)

        /**
         * 关闭软件盘
         */
        fun closeSoftKeyboard()
    }

    interface Presenter : IPresenter<View> {
        /**
         * 获取热门关键字的数据
         */
        fun requestHotWordData()

        /**
         * 查询搜索
         */
        fun querySearchData(words: String)

        /**
         * 加载更多
         */
        fun loadMoreData()
    }
}