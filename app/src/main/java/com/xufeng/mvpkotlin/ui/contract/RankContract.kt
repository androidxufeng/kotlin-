package com.xufeng.mvpkotlin.ui.contract

import com.xufeng.mvpkotlin.base.IBaseView
import com.xufeng.mvpkotlin.base.IPresenter
import com.xufeng.mvpkotlin.bean.HomeBean

/**
 * Ver 1.0, 18-9-27, xufeng, Create file
 */
interface RankContract {

    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun setRankList(itemList: List<HomeBean.Issue.Item>)
    }

    interface Presenter : IPresenter<View> {
        fun requestRankList(url: String)
    }
}