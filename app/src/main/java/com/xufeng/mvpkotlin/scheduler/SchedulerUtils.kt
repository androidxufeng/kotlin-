package com.xufeng.mvpkotlin.scheduler

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
object SchedulerUtils {

    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
}