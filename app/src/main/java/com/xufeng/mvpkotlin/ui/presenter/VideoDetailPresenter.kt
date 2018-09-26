package com.xufeng.mvpkotlin.ui.presenter

import android.app.Activity
import com.xufeng.mvpkotlin.app.MyApplication
import com.xufeng.mvpkotlin.app.dataFormat
import com.xufeng.mvpkotlin.base.BasePresenter
import com.xufeng.mvpkotlin.bean.HomeBean
import com.xufeng.mvpkotlin.model.VideoDetailModel
import com.xufeng.mvpkotlin.rx.scheduler.SchedulerUtils
import com.xufeng.mvpkotlin.ui.contract.VideoDetailContract
import com.xufeng.mvpkotlin.utils.DisplayManager
import com.xufeng.mvpkotlin.utils.NetworkUtil
import org.jetbrains.anko.toast

/**
 * Ver 1.0, 18-9-26, xufeng, Create file
 */
class VideoDetailPresenter : BasePresenter<VideoDetailContract.View>(), VideoDetailContract.Presenter {

    private val mVideoDetailModel by lazy {
        VideoDetailModel()
    }

    override fun requestRelatedVideo(id: Long) {
        val subscribe = mVideoDetailModel.requestRelatedData(id)
                .compose(SchedulerUtils.ioToMain())
                .subscribe({ issue ->
                    mView?.setRecentRelatedVideo(issue.itemList)
                }, { e ->
                    mView?.setErrorMsg(e.message.toString())
                })

        addSubscription(subscribe)
    }

    override fun loadVideoInfo(itemInfo: HomeBean.Issue.Item) {
        val playInfo = itemInfo.data?.playInfo

        val isWifi = NetworkUtil.isWifi(MyApplication.context)

        checkViewAttached()

        playInfo?.let {
            if (isWifi) {
                for (info in it) {
                    if (info.type == "high") {
                        mView?.setVideoUrl(info.url)
                        break
                    }
                }
            } else {
                for (info in it) {
                    if (info.type == "normal") {
                        mView?.setVideoUrl(info.url)
                        //Todo 待完善
                        (mView as Activity).toast("本次消耗${(mView as Activity)
                                .dataFormat(info.urlList[0].size)}流量")
                        break
                    }
                }
            }
        }

        val backgroundUrl = itemInfo.data?.cover?.blurred + "/thumbnail/${DisplayManager.getScreenHeight()!! -
                DisplayManager.dip2px(250f)!!}x${DisplayManager.getScreenWidth()}"
        backgroundUrl.let { mView?.setBackground(it) }

        mView?.setVideoInfo(itemInfo)
    }
}