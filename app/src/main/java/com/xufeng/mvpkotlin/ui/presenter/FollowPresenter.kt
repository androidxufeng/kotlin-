/*
 * Copyright (C), 2018, TP-LINK TECHNOLOGIES CO., LTD.
 *
 * ${FILE_NAME}
 *
 * Description
 *
 * Author xufeng
 *
 * Ver 1.0, 18-11-15, xufeng, Create file
 */

package com.xufeng.mvpkotlin.ui.presenter

import com.xufeng.mvpkotlin.base.BasePresenter
import com.xufeng.mvpkotlin.http.exception.ExceptionHandle
import com.xufeng.mvpkotlin.model.FollowModel
import com.xufeng.mvpkotlin.rx.scheduler.SchedulerUtils
import com.xufeng.mvpkotlin.ui.contract.FollowContract

class FollowPresenter : BasePresenter<FollowContract.View>(), FollowContract.Presenter {

    private val mModel by lazy {
        FollowModel()
    }

    private var mNextPageUrl: String? = null

    override fun requestFollowList() {
        checkViewAttached()
        mView?.showLoading()
        val disposable = mModel.requestFollowList()
                .compose(SchedulerUtils.ioToMain())
                .subscribe({ issue ->
                    mView?.apply {
                        dismissLoading()
                        mNextPageUrl = issue.nextPageUrl
                        setFollowInfo(issue)
                    }
                }, { t ->
                    mView?.run {
                        dismissLoading()
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

    override fun loadMoreData() {
        val disposable = mNextPageUrl?.let {
            mModel.loadMoreData(it)
                    .compose(SchedulerUtils.ioToMain())
                    .subscribe({ issus ->
                        mNextPageUrl = issus.nextPageUrl
                        mView?.run {
                            setFollowInfo(issus)
                        }
                    }, { t ->
                        mView?.showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    })
        }

        disposable?.also {
            addSubscription(it)
        }
    }
}