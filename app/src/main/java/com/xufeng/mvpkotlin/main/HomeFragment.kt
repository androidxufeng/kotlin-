package com.xufeng.mvpkotlin.main

import android.os.Bundle
import com.xufeng.mvpkotlin.R
import com.xufeng.mvpkotlin.base.BaseFragment
import com.xufeng.mvpkotlin.bean.FuckGoods
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.support.v4.toast

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
class HomeFragment : BaseFragment(), MainContract.View {

    private lateinit var mPresenter: MainPresenter

    private var mTitle: String? = null

    override fun showLoading() {
        tv_title.text = "加载中"
    }

    override fun dismissLoading() {
        tv_title.text = "消失了"
    }

    override fun showError(msg: String) {
        tv_title.text = msg
    }

    override fun showAndroidData(data: List<FuckGoods>) {
        tv_title.text = data.toString()
    }

    override fun getLayoutId(): Int {
        return R.layout.content_main
    }

    override fun lazyLoad() {
        mPresenter = MainPresenter()
        mPresenter.attachView(this)

        toast("title  $mTitle")

        tv_title.text = mTitle

        mPresenter.getAndroidData(1)
    }

    companion object {
        fun getInstance(title: String): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

}