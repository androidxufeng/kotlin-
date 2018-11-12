package com.xufeng.mvpkotlin.ui.presenter

import com.xufeng.mvpkotlin.base.BasePresenter
import com.xufeng.mvpkotlin.model.HotTabModel
import com.xufeng.mvpkotlin.rx.scheduler.SchedulerUtils
import com.xufeng.mvpkotlin.ui.contract.HotTabContract

/**
 * Ver 1.0, 18-9-27, xufeng, Create file
 */
class HotTabPresenter : BasePresenter<HotTabContract.View>(), HotTabContract.Presenter {

    private val mHotTabModel by lazy {
        HotTabModel()
    }

    override fun getTabInfo() {
        checkViewAttached()
        val disposable = mHotTabModel.getTabInfo()
                .compose(SchedulerUtils.ioToMain())
                .subscribe({ tabInfoBean ->
                    mView?.apply {
                        setTabInfo(tabInfoBean)
                    }
                }, { throwable ->
                    mView?.showError(throwable.toString())
                })

        addSubscription(disposable)
    }
}