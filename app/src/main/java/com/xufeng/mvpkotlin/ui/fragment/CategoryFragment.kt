package com.xufeng.mvpkotlin.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.xufeng.mvpkotlin.R
import com.xufeng.mvpkotlin.adapter.CategoryAdapter
import com.xufeng.mvpkotlin.base.BaseFragment
import com.xufeng.mvpkotlin.bean.CategoryBean
import com.xufeng.mvpkotlin.http.exception.ErrorStatus
import com.xufeng.mvpkotlin.ui.contract.CategoryContract
import com.xufeng.mvpkotlin.ui.presenter.CategoryPresenter
import com.xufeng.mvpkotlin.utils.DisplayManager
import com.xufeng.mvpkotlin.utils.StatusBarUtils
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.toast

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
class CategoryFragment : BaseFragment(), CategoryContract.View {

    private val mAdapter by lazy {
        CategoryAdapter(activity!!, mCategoryList, R.layout.item_category)
    }

    private val mPresenter by lazy {
        CategoryPresenter()
    }

    private var mCategoryList = ArrayList<CategoryBean>()

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun dismissLoading() {
        mLayoutStatusView?.showContent()
    }

    override fun showCategory(categoryList: ArrayList<CategoryBean>) {
        mAdapter.setData(categoryList)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        toast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    private var mTitle: String? = null

    /**
     * 伴生对象
     */
    companion object {
        fun getInstance(title: String): CategoryFragment {
            val fragment = CategoryFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun initView() {
        mPresenter.attachView(this)
        mLayoutStatusView = multipleStatusView
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = GridLayoutManager(context, 2)
        mRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                val position = parent!!.getChildAdapterPosition(view)
                val offset = DisplayManager.dip2px(2f)!!
                outRect?.set(if (position % 2 == 0) 0 else offset, offset,
                        if (position % 2 == 0) offset else 0, offset)
            }
        })

        //状态栏透明和间距处理
/*        StatusBarUtils.darkMode(activity!!)
        StatusBarUtils.setPaddingSmart(activity!!, toolbar)
        StatusBarUtils.setPaddingSmart(activity!!,mRecyclerView)*/
    }

    override fun lazyLoad() {
        mPresenter.getCategoryData()
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}