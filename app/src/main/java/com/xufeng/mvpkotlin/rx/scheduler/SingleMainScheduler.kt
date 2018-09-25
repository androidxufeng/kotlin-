package com.xufeng.mvpkotlin.rx.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
class SingleMainScheduler<T> private constructor(): BaseScheduler<T>(Schedulers.single(), AndroidSchedulers.mainThread()) {
}