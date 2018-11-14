/*
 * Copyright (C), 2018, TP-LINK TECHNOLOGIES CO., LTD.
 *
 * ${FILE_NAME}
 *
 * Description
 *
 * Author xufeng
 *
 * Ver 1.0, 18-11-12, xufeng, Create file
 */

package com.xufeng.mvpkotlin.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.xufeng.mvpkotlin.R
import com.xufeng.mvpkotlin.adapter.CategoryDetailAdapter
import com.xufeng.mvpkotlin.base.BaseFragment
import com.xufeng.mvpkotlin.bean.HomeBean
import com.xufeng.mvpkotlin.http.exception.ErrorStatus
import com.xufeng.mvpkotlin.ui.contract.RankContract
import com.xufeng.mvpkotlin.ui.presenter.RankPresenter
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.toast

class RankFragment : BaseFragment(), RankContract.View {

    private val mPresent by lazy {

        RankPresenter()
    }

    private val mAdapter by lazy {
        CategoryDetailAdapter(activity!!, mItemList, R.layout.item_category_detail)
    }

    private var mItemList = ArrayList<HomeBean.Issue.Item>()

    private var apiUrl: String? = null

    companion object {
        fun getInstance(apiUrl: String): RankFragment {
            val fragment = RankFragment()
            fragment.arguments = Bundle()
            fragment.apiUrl = apiUrl
            return fragment
        }
    }

    init {
        mPresent.attachView(this)
    }

    override fun getLayoutId(): Int = R.layout.fragment_rank

    override fun lazyLoad() {
        if (!apiUrl.isNullOrEmpty()) {
            mPresent.requestRankList(apiUrl!!)
        }
    }

    override fun initView() {
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mAdapter
        mLayoutStatusView = multipleStatusView
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        toast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    override fun setRankList(itemList: List<HomeBean.Issue.Item>) {
        mAdapter.addData(itemList)
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun dismissLoading() {
        mLayoutStatusView?.showContent()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresent.detachView()
    }
}