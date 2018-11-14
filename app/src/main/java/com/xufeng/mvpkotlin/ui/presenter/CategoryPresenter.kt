package com.xufeng.mvpkotlin.ui.presenter

import com.xufeng.mvpkotlin.base.BasePresenter
import com.xufeng.mvpkotlin.http.exception.ExceptionHandle
import com.xufeng.mvpkotlin.model.CategoryModel
import com.xufeng.mvpkotlin.rx.scheduler.SchedulerUtils
import com.xufeng.mvpkotlin.ui.contract.CategoryContract

/**
 * Ver 1.0, 18-9-26, xufeng, Create file
 */
class CategoryPresenter : BasePresenter<CategoryContract.View>(), CategoryContract.Presenter {

    private val mCategoryModel by lazy {
        CategoryModel()
    }

    override fun getCategoryData() {
        checkViewAttached()
        mView?.showLoading()

        val disposable = mCategoryModel.getCategoryData()
                .compose(SchedulerUtils.ioToMain())
                .subscribe({ list ->
                    mView?.apply {
                        dismissLoading()
                        showCategory(list)
                    }
                }, { t ->
                    mView?.apply {
                        dismissLoading()
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }
}