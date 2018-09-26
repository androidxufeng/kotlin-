package com.xufeng.mvpkotlin.ui.contract

import com.xufeng.mvpkotlin.base.IBaseView
import com.xufeng.mvpkotlin.base.IPresenter
import com.xufeng.mvpkotlin.bean.HomeBean

/**
 * Ver 1.0, 18-9-26, xufeng, Create file
 */
interface VideoDetailContract {

    interface View : IBaseView {
        /**
         * 设置视频播放源
         */
        fun setVideoUrl(url: String)

        /**
         * 设置视频信息
         */
        fun setVideoInfo(itemInfo: HomeBean.Issue.Item)

        fun setBackground(url: String)

        /**
         * 设置错误信息
         */
        fun setErrorMsg(errorMsg: String)

        /**
         * 最新相关视频
         */
        fun setRecentRelatedVideo(itemList: ArrayList<HomeBean.Issue.Item>)
    }

    interface Presenter : IPresenter<View> {
        /**
         * 加载视频信息
         */
        fun loadVideoInfo(itemInfo: HomeBean.Issue.Item)

        /**
         * 请求相关的视频数据
         */
        fun requestRelatedVideo(id: Long)
    }
}