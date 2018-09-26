package com.xufeng.mvpkotlin.ui.activity

import android.content.res.Configuration
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.orhanobut.logger.Logger
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import com.xufeng.mvpkotlin.R
import com.xufeng.mvpkotlin.adapter.VideoDetailAdapter
import com.xufeng.mvpkotlin.app.Constant
import com.xufeng.mvpkotlin.base.BaseActivity
import com.xufeng.mvpkotlin.bean.HomeBean
import com.xufeng.mvpkotlin.glide.GlideApp
import com.xufeng.mvpkotlin.ui.contract.VideoDetailContract
import com.xufeng.mvpkotlin.ui.presenter.VideoDetailPresenter
import com.xufeng.mvpkotlin.utils.VideoLisenter
import kotlinx.android.synthetic.main.activity_video_detail.*

/**
 * Ver 1.0, 18-9-26, xufeng, Create file
 */
class VideoDetailActivity : BaseActivity(), VideoDetailContract.View {

    private val mAdapter by lazy {
        VideoDetailAdapter(this, itemList)
    }

    private val itemList = ArrayList<HomeBean.Issue.Item>()

    private var orientationUtils: OrientationUtils? = null

    private var isPlay: Boolean = false

    private var isPause: Boolean = false

    private val mPresenter by lazy {
        VideoDetailPresenter()
    }

    private var mItemData: HomeBean.Issue.Item? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_video_detail
    }

    override fun initData() {
        mItemData = intent.getSerializableExtra(Constant.BUNDLE_VIDEO_DATA) as HomeBean.Issue.Item
        mPresenter.loadVideoInfo(mItemData!!)
    }

    override fun initView() {
        mPresenter.attachView(this)
        orientationUtils = OrientationUtils(this, mVideoView)
        // 是否旋转
        mVideoView.isRotateViewAuto = false
        //是否可以欢动旋转
        mVideoView.setIsTouchWiget(true)
        mVideoView.backButton.setOnClickListener {
            onBackPressed()
        }
        mVideoView.setStandardVideoAllCallBack(object : VideoLisenter {
            override fun onPrepared(url: String, vararg objects: Any) {
                super.onPrepared(url, *objects)
                orientationUtils?.isEnable = true
                isPlay = true
            }

            override fun onAutoComplete(url: String, vararg objects: Any) {
                super.onAutoComplete(url, *objects)
                Logger.d("***** onAutoPlayComplete **** ")
            }

            override fun onEnterFullscreen(url: String, vararg objects: Any) {
                super.onEnterFullscreen(url, *objects)
                Logger.d("***** onEnterFullscreen **** ")
            }

            override fun onQuitFullscreen(url: String, vararg objects: Any) {
                super.onQuitFullscreen(url, *objects)
                Logger.d("***** onQuitFullscreen **** ")
                orientationUtils?.backToProtVideo()
            }
        })

        mVideoView.fullscreenButton.setOnClickListener {
            orientationUtils?.resolveByClick()

            mVideoView.startWindowFullscreen(this, true, true)
        }

        mVideoView.setLockClickListener { _, lock ->
            orientationUtils?.isEnable = !lock
        }

        rv_video_detail.layoutManager = LinearLayoutManager(this)
        rv_video_detail.adapter = mAdapter

        mAdapter.setOnItemDetailClick {
            mPresenter.loadVideoInfo(it)
        }
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun setVideoUrl(url: String) {
        mVideoView.setUp(url, false, "")
        mVideoView.startPlayLogic()
    }

    override fun setVideoInfo(itemInfo: HomeBean.Issue.Item) {
        mItemData = itemInfo
        mAdapter.addData(itemInfo)
        //请求相关视频信息
        mPresenter.requestRelatedVideo(itemInfo.data!!.id)
    }

    override fun setRecentRelatedVideo(itemList: ArrayList<HomeBean.Issue.Item>) {
        mAdapter.addData(itemList)
    }

    override fun setBackground(url: String) {
        GlideApp.with(this).load(url).apply(RequestOptions()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .centerCrop())
                .into(mVideoBackground)
    }

    override fun setErrorMsg(errorMsg: String) {
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (isPlay && !isPause) {
            mVideoView.onConfigurationChanged(this, newConfig, orientationUtils)
        }
    }

    override fun onBackPressed() {
        orientationUtils?.backToProtVideo()
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }


    override fun onResume() {
        super.onResume()
        getCurPlay().onVideoResume()
        isPause = false
    }

    override fun onPause() {
        super.onPause()
        getCurPlay().onVideoPause()
        isPause = true
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoPlayer.releaseAllVideos()
        orientationUtils?.releaseListener()
    }

    private fun getCurPlay(): GSYVideoPlayer {
        return if (mVideoView.fullWindowPlayer != null) {
            mVideoView.fullWindowPlayer
        } else mVideoView
    }

}