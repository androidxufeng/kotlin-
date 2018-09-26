package com.xufeng.mvpkotlin.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
abstract class BasePresenter<V : IBaseView> : IPresenter<V> {

    var mView: V? = null
            // 不允许外部赋值
        private set

    private val mCompositeDisposable = CompositeDisposable()

    private val isViewAttached: Boolean
        get() = mView != null

    override fun attachView(view: V) {
        mView = view
    }

    override fun detachView() {
        mView = null
        if (!mCompositeDisposable.isDisposed) {
            mCompositeDisposable.clear()
        }
    }

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    fun addSubscription(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }

    private class MvpViewNotAttachedException internal constructor() :
            RuntimeException("Please call IPresenter.attachView(IBaseView) before" +
                    " requesting data to the IPresenter")

}