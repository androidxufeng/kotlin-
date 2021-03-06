package com.xufeng.mvpkotlin.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.xufeng.mvpkotlin.R
import com.xufeng.mvpkotlin.base.BaseFragment
import com.xufeng.mvpkotlin.base.BaseFragmentAdapter
import com.xufeng.mvpkotlin.bean.TabInfoBean
import com.xufeng.mvpkotlin.ui.contract.HotTabContract
import com.xufeng.mvpkotlin.ui.presenter.HotTabPresenter
import com.xufeng.mvpkotlin.utils.StatusBarUtils
import kotlinx.android.synthetic.main.fragment_hot.*

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
class HotFragment : BaseFragment(), HotTabContract.View {

    private var mTitle: String? = null

    private val mPresenter by lazy {
        HotTabPresenter()
    }

    private val mTabTitleList = ArrayList<String>()

    private val mFragmentList = ArrayList<Fragment>()

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

    init {
        mPresenter.attachView(this)
    }

    override fun initView() {
        //状态栏透明和间距处理
        StatusBarUtils.darkMode(activity!!)
        StatusBarUtils.setPaddingSmart(activity!!, toolbar)
    }

    override fun lazyLoad() {
        mPresenter.getTabInfo()
    }

    override fun getLayoutId(): Int = R.layout.fragment_hot

    override fun setTabInfo(tabInfoBean: TabInfoBean) {
        tabInfoBean.tabInfo.tabList.mapTo(mTabTitleList) {
            it.name
        }

        tabInfoBean.tabInfo.tabList.mapTo(mFragmentList) {
            RankFragment.getInstance(it.apiUrl)
        }

        mViewPager.adapter = BaseFragmentAdapter(childFragmentManager!!, mFragmentList, mTabTitleList)
        mTabLayout.setupWithViewPager(mViewPager)
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun showError(msg: String) {
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}