package com.xufeng.mvpkotlin.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.header.MaterialHeader
import com.xufeng.mvpkotlin.R
import com.xufeng.mvpkotlin.adapter.HomeAdapter
import com.xufeng.mvpkotlin.base.BaseFragment
import com.xufeng.mvpkotlin.bean.HomeBean
import com.xufeng.mvpkotlin.http.exception.ErrorStatus
import com.xufeng.mvpkotlin.ui.activity.SearchActivity
import com.xufeng.mvpkotlin.ui.contract.HomeContract
import com.xufeng.mvpkotlin.ui.presenter.HomePresenter
import com.xufeng.mvpkotlin.utils.StatusBarUtils
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.toast
import java.text.SimpleDateFormat
import java.util.*

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
class HomeFragment : BaseFragment(), HomeContract.View {

    private val mPresenter by lazy {
        HomePresenter()
    }

    private var mHomeAdapter: HomeAdapter? = null

    private var loadingMore = false

    private var mTitle: String? = null

    private var mNum = 1

    private var isRefresh = false

    private val mSimpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }

    private var mMaterialHeader: MaterialHeader? = null

    override fun showLoading() {
        if (!isRefresh) {
            isRefresh = false
            mLayoutStatusView?.showLoading()
        }
    }

    override fun dismissLoading() {
        mRefreshLayout.finishRefresh()
        mLayoutStatusView?.showContent()
    }

    override fun showError(msg: String, errorCode: Int) {
        toast(msg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    override fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>) {
        loadingMore = false
        mHomeAdapter?.addItemData(itemList)
    }

    override fun showHomeData(data: HomeBean) {
        Logger.d(data)
        mHomeAdapter = HomeAdapter(activity!!, data.issueList[0].itemList)
        mHomeAdapter?.setBanner(data.issueList[0].count)
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.adapter = mHomeAdapter
    }

    private val linearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    override fun initView() {
        mPresenter.attachView(this)

        //内容跟随偏移
        mRefreshLayout.setEnableHeaderTranslationContent(true)
        mRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mPresenter.requestHomeData(mNum)
        }

        mMaterialHeader = mRefreshLayout.refreshHeader as MaterialHeader?
        //打开下拉刷新区域块背景:
        mMaterialHeader?.setShowBezierWave(true)
        //设置下拉刷新主题颜色
        mRefreshLayout.setPrimaryColorsId(R.color.app_color_theme_6, R.color.color_title_bg)


        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val childCount = mRecyclerView.childCount
                    val itemCount = mRecyclerView.layoutManager.itemCount
                    val firstVisibleItem = (mRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisibleItem + childCount == itemCount) {
                        if (!loadingMore) {
                            loadingMore = true
                            mPresenter.loadMoreData()
                        }
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                if (currentVisibleItemPosition == 0) {
                    //背景设置为透明
                    toolbar.setBackgroundColor(Color.TRANSPARENT)
                    iv_search.setImageResource(R.drawable.ic_action_search_white)
                    tv_header_title.text = ""
                } else {
                    if (mHomeAdapter?.mData!!.size > 1) {
                        toolbar.setBackgroundColor(getColor(R.color.color_title_bg))
                        iv_search.setImageResource(R.drawable.ic_action_search_black)
                        val itemList = mHomeAdapter!!.mData
                        val item = itemList[currentVisibleItemPosition + mHomeAdapter!!.bannerItemSize - 1]
                        if (item.type == "textHeader") {
                            tv_header_title.text = item.data?.text
                        } else {
                            tv_header_title.text = mSimpleDateFormat.format(item.data?.date)
                        }
                    }
                }
            }
        })

        iv_search.setOnClickListener { openSearchActivity() }
        mLayoutStatusView = multipleStatusView

        //状态栏透明和间距处理
        StatusBarUtils.darkMode(activity!!)
        StatusBarUtils.setPaddingSmart(activity!!, toolbar)
    }

    private fun openSearchActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, iv_search, iv_search.transitionName)
            startActivity(Intent(activity, SearchActivity::class.java), options.toBundle())
        } else {
            startActivity(Intent(activity, SearchActivity::class.java))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun lazyLoad() {
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

    fun getColor(colorId: Int): Int {
        return resources.getColor(colorId)
    }

}