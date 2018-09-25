package com.xufeng.mvpkotlin.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.xufeng.mvpkotlin.R
import com.xufeng.mvpkotlin.adapter.HomeAdapter
import com.xufeng.mvpkotlin.base.BaseFragment
import com.xufeng.mvpkotlin.bean.HomeBean
import com.xufeng.mvpkotlin.ui.contract.HomeContract
import com.xufeng.mvpkotlin.ui.presenter.HomePresenter
import kotlinx.android.synthetic.main.text.*

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
class HomeFragment : BaseFragment(), HomeContract.View {

    private lateinit var mPresenter: HomePresenter

    private var mHomeAdapter: HomeAdapter? = null

    private var mTitle: String? = null

    private var mNum = 1

    override fun showLoading() {
        mRefreshLayout.autoRefresh()
    }

    override fun dismissLoading() {
        mRefreshLayout.finishRefresh()
    }

    override fun showError(msg: String) {

    }

    override fun showHomeData(data: HomeBean) {
        mHomeAdapter = HomeAdapter(activity!!, data.issueList[0].itemList)
        mHomeAdapter?.setBanner(data.issueList[0].count)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mHomeAdapter
    }

    override fun initView() {
        mRefreshLayout.setOnRefreshListener { mPresenter.requestHomeData(mNum) }
    }

    override fun getLayoutId(): Int {
        return R.layout.content_main
    }

    override fun lazyLoad() {
        mPresenter = HomePresenter()
        mPresenter.attachView(this)
        mPresenter.requestHomeData(1)
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