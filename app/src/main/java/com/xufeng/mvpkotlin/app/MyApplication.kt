package com.xufeng.mvpkotlin.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.xufeng.mvpkotlin.R
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
        test()
    }

    init {
        //static 代码段可以防止内存泄露
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater { context, layout ->
            //全局设置主题颜色
            layout?.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)
            //指定为经典Header，默认是 贝塞尔雷达Header
            ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate)
        }

        SmartRefreshLayout.setDefaultRefreshFooterCreater { context, _ ->
            //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate)
        }
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