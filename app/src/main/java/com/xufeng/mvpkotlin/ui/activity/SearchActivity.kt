package com.xufeng.mvpkotlin.ui.activity

import android.annotation.TargetApi
import android.graphics.Typeface
import android.os.Build
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.google.android.flexbox.*
import com.xufeng.mvpkotlin.R
import com.xufeng.mvpkotlin.adapter.CategoryDetailAdapter
import com.xufeng.mvpkotlin.adapter.HotKeywordsAdapter
import com.xufeng.mvpkotlin.app.MyApplication
import com.xufeng.mvpkotlin.base.BaseActivity
import com.xufeng.mvpkotlin.bean.HomeBean
import com.xufeng.mvpkotlin.http.exception.ErrorStatus
import com.xufeng.mvpkotlin.ui.contract.SearchContract
import com.xufeng.mvpkotlin.ui.presenter.SearchPresenter
import com.xufeng.mvpkotlin.utils.StatusBarUtils
import com.xufeng.mvpkotlin.utils.ViewAnimUtils
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.toast

class SearchActivity : BaseActivity(), SearchContract.View {

    private val mPresenter by lazy {
        SearchPresenter()
    }

    private val mAdapter by lazy {
        CategoryDetailAdapter(this, mList, R.layout.item_category_detail)
    }

    private var mList = ArrayList<HomeBean.Issue.Item>()

    private var mHotKeywordsAdapter: HotKeywordsAdapter? = null

    private var mTypeface: Typeface? = null

    private var mKeyWords: String? = null

    private var mLoadingMore = false

    init {
        mPresenter.attachView(this)
        mTypeface = Typeface.createFromAsset(MyApplication.context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    override fun initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setUpEnterAnimation() // 入场动画
            setUpExitAnimation() // 退场动画
        } else {
            setUpView()
        }
    }

    override fun initView() {
        tv_title_tip.typeface = mTypeface
        tv_hot_search_words.typeface = mTypeface

        mRecyclerView_result.layoutManager = LinearLayoutManager(this)
        mRecyclerView_result.adapter = mAdapter

        mRecyclerView_result.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = mRecyclerView_result.layoutManager.itemCount
                val lastVisibleItem = (mRecyclerView_result.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!mLoadingMore && lastVisibleItem == (itemCount - 1)) {
                    mLoadingMore = true
                    mPresenter.loadMoreData()
                }
            }
        })

        tv_cancel.setOnClickListener {
            onBackPressed()
        }

        et_search_view.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mKeyWords = et_search_view.text.toString().trim()
                    if (mKeyWords.isNullOrEmpty()) {
                        toast("请输入你感兴趣的关键词")
                    } else {
                        mPresenter.querySearchData(mKeyWords!!)
                    }
                }
                return false
            }
        })
        mLayoutStatusView = multipleStatusView

        //状态栏透明和间距处理
        StatusBarUtils.darkMode(this)
        StatusBarUtils.setPaddingSmart(this, toolbar)
    }

    override fun start() {
        mPresenter.requestHotWordData()
    }

    override fun getLayoutId(): Int = R.layout.activity_search

    override fun setHotWordData(string: ArrayList<String>) {
        showHotWordView()
        mHotKeywordsAdapter = HotKeywordsAdapter(this, string, R.layout.item_flow_text)
        val flexBoxLayoutManager = FlexboxLayoutManager(this)
        flexBoxLayoutManager.flexWrap = FlexWrap.WRAP      //按正常方向换行
        flexBoxLayoutManager.flexDirection = FlexDirection.ROW   //主轴为水平方向，起点在左端
        flexBoxLayoutManager.alignItems = AlignItems.CENTER    //定义项目在副轴轴上如何对齐
        flexBoxLayoutManager.justifyContent = JustifyContent.FLEX_START  //多个轴对齐方式
        mRecyclerView_hot.layoutManager = flexBoxLayoutManager
        mRecyclerView_hot.adapter = mHotKeywordsAdapter
        //设置 Tag 的点击事件
        mHotKeywordsAdapter?.setTagItemClickListener {
            mKeyWords = it
            mPresenter.querySearchData(it)
        }
    }

    override fun setSearchResult(issue: HomeBean.Issue) {
        mLoadingMore = false
        hideHotWordView()
        tv_search_count.visibility = View.VISIBLE
        tv_search_count.text = String.format(resources.getString(R.string.search_result_count), mKeyWords, issue.total)
        mList = issue.itemList
        mAdapter.addData(issue.itemList)
    }

    override fun setEmptyView() {
        toast("抱歉，没有找到相匹配的内容")
        hideHotWordView()
        tv_search_count.visibility = View.GONE
        mLayoutStatusView?.showEmpty()
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        toast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun dismissLoading() {
        mLayoutStatusView?.showContent()
    }

    private fun setUpView() {
        val animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        animation.duration = 300
        rel_container.startAnimation(animation)
        rel_container.visibility = View.VISIBLE
        //打开软键盘
        openKeyBoard(et_search_view, applicationContext)
    }

    /**
     * 退场动画
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUpExitAnimation() {
        val fade = Fade()
        window.returnTransition = fade
        fade.duration = 300
    }

    /**
     * 进场动画
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUpEnterAnimation() {
        val transition = TransitionInflater.from(this)
                .inflateTransition(R.transition.arc_motion)
        window.sharedElementEnterTransition = transition
        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionStart(transition: Transition) {
            }

            override fun onTransitionEnd(transition: Transition) {
                transition.removeListener(this)
                animateRevealShow()
            }

            override fun onTransitionCancel(transition: Transition) {
            }

            override fun onTransitionPause(transition: Transition) {
            }

            override fun onTransitionResume(transition: Transition) {
            }
        })
    }

    /**
     * 展示动画
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun animateRevealShow() {
        ViewAnimUtils.animateRevealShow(
                this, rel_frame,
                fab_circle.width / 2, R.color.backgroundColor,
                object : ViewAnimUtils.OnRevealAnimationListener {
                    override fun onRevealHide() {
                    }

                    override fun onRevealShow() {
                        setUpView()
                    }
                })
    }

    // 返回事件
    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewAnimUtils.animateRevealHide(
                    this, rel_frame,
                    fab_circle.width / 2, R.color.backgroundColor,
                    object : ViewAnimUtils.OnRevealAnimationListener {
                        override fun onRevealHide() {
                            defaultBackPressed()
                        }

                        override fun onRevealShow() {
                        }
                    })
        } else {
            defaultBackPressed()
        }
    }

    // 默认回退
    private fun defaultBackPressed() {
        closeKeyBoard(et_search_view, applicationContext)
        super.onBackPressed()
    }

    override fun closeSoftKeyboard() {
        closeKeyBoard(et_search_view, applicationContext)
    }

    /**
     * 隐藏热门关键字的 View
     */
    private fun hideHotWordView() {
        layout_hot_words.visibility = View.GONE
        layout_content_result.visibility = View.VISIBLE
    }

    /**
     * 显示热门关键字的 流式布局
     */
    private fun showHotWordView() {
        layout_hot_words.visibility = View.VISIBLE
        layout_content_result.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

}