package com.xufeng.mvpkotlin.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.xufeng.mvpkotlin.R
import com.xufeng.mvpkotlin.base.BaseFragment
import com.xufeng.mvpkotlin.ui.activity.AboutActivity
import com.xufeng.mvpkotlin.ui.activity.ProfileHomePageActivity
import com.xufeng.mvpkotlin.utils.StatusBarUtils
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
class MineFragment : BaseFragment(), View.OnClickListener {
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

        iv_avatar.setOnClickListener(this)
        tv_view_homepage.setOnClickListener(this)
        iv_about.setOnClickListener(this)
    }

    override fun lazyLoad() {
    }

    override fun getLayoutId(): Int = R.layout.fragment_mine

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_avatar, R.id.tv_view_homepage -> {
                val intent = Intent(activity, ProfileHomePageActivity::class.java)
                startActivity(intent)
            }
            R.id.iv_about -> {
                val intent = Intent(activity, AboutActivity::class.java)
                startActivity(intent)
            }
        }
    }
}