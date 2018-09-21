package com.xufeng.mvpkotlin.main

import android.os.Bundle
import com.xufeng.mvpkotlin.R
import com.xufeng.mvpkotlin.base.BaseFragment
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.support.v4.toast

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
class HotFragment : BaseFragment() {
    private var mTitle: String? = null

    /**
     * 伴生对象
     */
    companion object {
        fun getInstance(title: String): HotFragment {
            val fragment = HotFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun lazyLoad() {
        toast("Hot title" + mTitle)
        tv_title.text = mTitle
    }

    override fun getLayoutId(): Int = R.layout.content_main
}