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
import com.xufeng.mvpkotlin.model.SearchModel
import com.xufeng.mvpkotlin.rx.scheduler.SchedulerUtils
import com.xufeng.mvpkotlin.ui.contract.SearchContract


class SearchPresenter : BasePresenter<SearchContract.View>(), SearchContract.Presenter {


    private val mModel by lazy {
        SearchModel()
    }

    private var mNextPageUrl: String? = null

    override fun requestHotWordData() {
        checkViewAttached()
        addSubscription(disposable = mModel.requestHotWordData()
                .compose(SchedulerUtils.ioToMain())
                .subscribe({ strings ->
                    mView?.apply {
                        setHotWordData(strings)
                    }
                }, { throwable ->
                    mView?.apply {
                        showError(throwable.toString())
                    }

                }))
    }

    override fun querySearchData(words: String) {
        checkViewAttached()
        mView?.showLoading()
        addSubscription(
                disposable = mModel.getSearchResult(words)
                        .compose(SchedulerUtils.ioToMain())
                        .subscribe(
                                { issue ->
                                    mView?.apply {
                                        dismissLoading()
                                        if (issue.count > 0 && issue.itemList.size > 0) {
                                            mNextPageUrl = issue.nextPageUrl
                                            setSearchResult(issue)
                                        }
                                    }
                                }, { throwable ->
                            mView?.apply {
                                dismissLoading()
                                showError(throwable.toString())
                            }
                        }
                        )
        )
    }

    override fun loadMoreData() {
        checkViewAttached()
        mNextPageUrl?.let {
            addSubscription(disposable = mModel.loadMoreData(it)
                    .compose(SchedulerUtils.ioToMain())
                    .subscribe({ issue ->
                        mNextPageUrl = issue.nextPageUrl
                        mView?.apply {
                            setSearchResult(issue)
                        }
                    }, { throwable ->
                        mView?.apply {
                            showError(throwable.toString())
                        }
                    }))
        }
    }

}