/*
 * Copyright (C), 2018, TP-LINK TECHNOLOGIES CO., LTD.
 *
 * RankPresenter.kt
 *
 * Description
 *
 * Author xufeng
 *
 * Ver 1.0, 18-11-12, xufeng, Create file
 */

package com.xufeng.mvpkotlin.ui.presenter

import com.xufeng.mvpkotlin.base.BasePresenter
import com.xufeng.mvpkotlin.model.RankModel
import com.xufeng.mvpkotlin.rx.scheduler.SchedulerUtils
import com.xufeng.mvpkotlin.ui.contract.RankContract


class RankPresenter : BasePresenter<RankContract.View>(), RankContract.Presenter {

    private val mModel by lazy {
        RankModel()
    }

    override fun requestRankList(url: String) {
        checkViewAttached()
        mView?.showLoading()
        val disposable = mModel.requesetRankList(url)
                .compose(SchedulerUtils.ioToMain())
                .subscribe({ issue ->
                    mView?.apply {
                        dismissLoading()
                        setRankList(issue.itemList)
                    }
                }, { throwable ->
                    mView?.apply {
                        dismissLoading()
                        showError(throwable.toString())
                    }
                })

        addSubscription(disposable)
    }

}