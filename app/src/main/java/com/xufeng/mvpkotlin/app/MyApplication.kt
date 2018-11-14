package com.xufeng.mvpkotlin.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.xufeng.mvpkotlin.BuildConfig
import com.xufeng.mvpkotlin.R
import com.xufeng.mvpkotlin.utils.DisplayManager
import kotlin.properties.Delegates

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 **/
class MyApplication : Application() {

    companion object {
        private val TAG = "MyApplication"

        var context: Context by Delegates.notNull()
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
        initConfig()
        DisplayManager.init(this)
    }

    init {
        /* //static 代码段可以防止内存泄露
         //设置全局的Header构建器
         SmartRefreshLayout.setDefaultRefreshHeaderCreater { context, layout ->
             //全局设置主题颜色
             layout?.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)
             //指定为经典Header，默认是 贝塞尔雷达Header
             ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate)
         }

         SmartRefreshLayout.setDefaultRefreshFooterCreater { context, _ ->
             //指定为经典Footer，默认是 BallPulseFooter
             ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Scale)
         }*/
        SmartRefreshLayout.setDefaultRefreshHeaderCreater { context, layout ->
            layout?.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white) //全局设置主题颜色
            MaterialHeader(context)
        }
    }

    private fun initConfig() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // 隐藏线程信息 默认：显示
                .methodCount(0)         // 决定打印多少行（每一行代表一个方法）默认：2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("xu_master")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    private val mActivityLifecycleCallbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityResumed(activity: Activity) {
            Log.d(TAG, "onCreated: " + activity.componentName.className)
        }

        override fun onActivityStarted(activity: Activity) {
            Log.d(TAG, "onStart: " + activity.componentName.className)
        }

        override fun onActivityDestroyed(activity: Activity) {
            Log.d(TAG, "onDestroy: " + activity.componentName.className)
        }

        override fun onActivitySaveInstanceState(activity: Activity?, p1: Bundle?) {
        }

        override fun onActivityStopped(activity: Activity?) {
        }

        override fun onActivityCreated(activity: Activity?, p1: Bundle?) {
            Log.d(TAG, "onActivityCreated: " + activity?.componentName?.className)
        }

        override fun onActivityPaused(activity: Activity?) {
        }

    }
}