package com.xufeng.mvpkotlin.main

import android.util.Log
import com.xufeng.mvpkotlin.base.BasePresenter
import com.xufeng.mvpkotlin.bean.FuckGoods
import com.xufeng.mvpkotlin.http.ResponseResult
import com.xufeng.mvpkotlin.http.RetrofitManager
import com.xufeng.mvpkotlin.scheduler.SchedulerUtils

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
class MainPresenter : BasePresenter<MainContract.View>(), MainContract.Presenter {
    override fun getAndroidData(page: Int) {

        mView?.showLoading()

        val disposable = RetrofitManager.service.getAndroidData(page)
                .compose(SchedulerUtils.ioToMain())
                .subscribe({ result: ResponseResult<List<FuckGoods>> ->
                    if (result.error) {
                        Log.d("TAG", "返回error")
                    } else {
                        mView?.dismissLoading()
                        mView?.showAndroidData(result.result)
                    }
                }, { t ->
                    mView.apply {
                        mView?.dismissLoading()
                        mView?.showError(t.toString())
                    }
                }
                )
        addSubscription(disposable)
    }
}