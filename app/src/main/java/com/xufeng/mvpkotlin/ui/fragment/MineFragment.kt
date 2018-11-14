package com.xufeng.mvpkotlin.ui.fragment

import android.os.Bundle
import com.xufeng.mvpkotlin.R
import com.xufeng.mvpkotlin.base.BaseFragment
import com.xufeng.mvpkotlin.utils.StatusBarUtils
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
class MineFragment : BaseFragment() {
    private var mTitle: String? = null

    /**
     * 伴生对象
     */
    companion object {
        fun getInstance(title: String): MineFragment {
            val fragment = MineFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun initView() {
        //状态栏透明和间距处理
        StatusBarUtils.darkMode(activity!!)
        StatusBarUtils.setPaddingSmart(activity!!, toolbar)
    }

    override fun lazyLoad() {
    }

    override fun getLayoutId(): Int = R.layout.fragment_mine
}