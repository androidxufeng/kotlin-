package com.xufeng.mvpkotlin.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
class NewThreadMainScheduler<T> private constructor():
        BaseScheduler<T>(Schedulers.newThread(), AndroidSchedulers.mainThread()) {
}