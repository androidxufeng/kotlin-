package com.xufeng.mvpkotlin.ui.activity

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.orhanobut.logger.Logger
import com.xufeng.mvpkotlin.R
import com.xufeng.mvpkotlin.adapter.CategoryDetailAdapter
import com.xufeng.mvpkotlin.app.Constant
import com.xufeng.mvpkotlin.base.BaseActivity
import com.xufeng.mvpkotlin.bean.CategoryBean
import com.xufeng.mvpkotlin.bean.HomeBean
import com.xufeng.mvpkotlin.glide.GlideApp
import com.xufeng.mvpkotlin.ui.contract.CategoryDetailContract
import com.xufeng.mvpkotlin.ui.presenter.CategoryDetailPresenter
import kotlinx.android.synthetic.main.activity_category_detail.*

/**
 * Ver 1.0, 18-9-26, xufeng, Create file
 */
class CategoryDetailActivity : BaseActivity() ,CategoryDetailContract.View{

    private val mItemList = ArrayList<HomeBean.Issue.Item>()

    private var mCateGroyData: CategoryBean? = null

    private var loadingMore = false

    private val mPresenter by lazy {
        CategoryDetailPresenter()
    }

    private val mAdapter by lazy {
        CategoryDetailAdapter(this, mItemList, R.layout.item_category_detail)
    }

    override fun initData() {
        mCateGroyData = intent.getSerializableExtra(Constant.BUNDLE_CATEGORY_DATA) as CategoryBean
    }

    override fun initView() {
        mPresenter.attachView(this)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        // 加载headerImage
        GlideApp.with(this)
                .load(mCateGroyData?.headerImage)
                .placeholder(R.color.color_darker_gray)
                .into(imageView)
        tv_category_desc.text ="#${mCateGroyData?.description}#"

        collapsing_toolbar_layout.title = mCateGroyData?.name
        collapsing_toolbar_layout.setExpandedTitleColor(Color.WHITE) //设置还没收缩时状态下字体颜色
        collapsing_toolbar_layout.setCollapsedTitleTextColor(Color.BLACK) //设置收缩后Toolbar上字体的颜色

        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        //实现自动加载
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = mRecyclerView.layoutManager.itemCount
                val lastVisibleItem = (mRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loadingMore && lastVisibleItem == (itemCount - 1)) {
                    loadingMore = true
                    mPresenter.loadMoreData()
                }
            }
        })
    }

    override fun start() {
        //获取当前分类列表
        mPresenter.getCategoryDetailList(mCateGroyData?.id!!)
    }

    override fun getLayoutId(): Int = R.layout.activity_category_detail

    override fun showLoading() {
       multipleStatusView.showLoading()
    }

    override fun dismissLoading() {
        multipleStatusView.showContent()
    }

    override fun showCategoryDetail(itemList: ArrayList<HomeBean.Issue.Item>) {
        loadingMore = false
        mAdapter.addData(itemList)
    }

    override fun showError(errorMsg: String) {
        multipleStatusView.showError()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}