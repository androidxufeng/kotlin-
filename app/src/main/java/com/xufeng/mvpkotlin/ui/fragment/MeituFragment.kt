package com.xufeng.mvpkotlin.ui.fragment

import android.os.Bundle
import com.xufeng.mvpkotlin.R
import com.xufeng.mvpkotlin.base.BaseFragment
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.support.v4.toast

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
class MeituFragment : BaseFragment() {
    private var mTitle: String? = null

    /**
     * 伴生对象
     */
    companion object {
        fun getInstance(title: String): MeituFragment {
            val fragment = MeituFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun initView() {

    }

    override fun lazyLoad() {
        toast("meitu title" + mTitle)
    }

    override fun getLayoutId(): Int = R.layout.content_main
}