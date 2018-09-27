package com.xufeng.mvpkotlin.ui.presenter

import com.orhanobut.logger.Logger
import com.xufeng.mvpkotlin.base.BasePresenter
import com.xufeng.mvpkotlin.model.CategoryDetailModel
import com.xufeng.mvpkotlin.rx.scheduler.SchedulerUtils
import com.xufeng.mvpkotlin.ui.contract.CategoryDetailContract

/**
 * Ver 1.0, 18-9-26, xufeng, Create file
 */
class CategoryDetailPresenter : BasePresenter<CategoryDetailContract.View>(), CategoryDetailContract.Presenter {

    private var nextPageUrl = ""


    override fun getCategoryDetailList(id: Long) {
        checkViewAttached()
        mView?.showLoading()

        val disposable = mCategoryDetailModel.getCategoryDetailList(id)
                .compose(SchedulerUtils.ioToMain())
                .subscribe({ issue ->
                    mView?.apply {
                        dismissLoading()
                        nextPageUrl = issue.nextPageUrl
                        showCategoryDetail(issue.itemList)
                    }
                }, { t ->
                    mView?.apply {
                        dismissLoading()
                        showError(t.toString())
                    }
                })
        addSubscription(disposable)
    }

    private val mCategoryDetailModel by lazy {
        CategoryDetailModel()
    }

    override fun loadMoreData() {
        mView?.showLoading()

        val disposable = mCategoryDetailModel.loadMoreData(nextPageUrl)
                .compose(SchedulerUtils.ioToMain())
                .subscribe({ issue ->
                    mView?.apply {
                        dismissLoading()
                        nextPageUrl = issue.nextPageUrl
                        showCategoryDetail(issue.itemList)
                    }
                }, { t ->
                    mView?.apply {
                        dismissLoading()
                        showError(t.toString())
                    }
                })
        addSubscription(disposable)
    }
}