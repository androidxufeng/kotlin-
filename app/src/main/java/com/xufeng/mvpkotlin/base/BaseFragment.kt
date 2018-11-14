package com.xufeng.mvpkotlin.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xufeng.multiplestatusview.MultipleStatusView
import org.jetbrains.anko.support.v4.toast

/**
 * create by xufeng 2018/9/20
 */
abstract class BaseFragment : Fragment() {

    /**
     * 多种状态的 View 的切换
     */
    protected var mLayoutStatusView: MultipleStatusView? = null
    /**
     * 视图是否加载完毕
     */
    private var isViewPrepare: Boolean = false

    /**
     * 数据是否加载过了
     */
    private var hasLoadData: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), null)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepare = true
        initView()
        lazyLoadDataIfPrepared()
        mLayoutStatusView?.setOnClickListener{
            toast("您点击了重试视图----baseFragment")
            lazyLoad()
        }
    }
    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
        }
    }


    //abstract
    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun lazyLoad()

    abstract fun initView()

}