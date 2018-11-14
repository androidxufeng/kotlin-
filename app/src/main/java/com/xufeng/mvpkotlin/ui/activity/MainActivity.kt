package com.xufeng.mvpkotlin.ui.activity

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.xufeng.mvpkotlin.R
import com.xufeng.mvpkotlin.base.BaseActivity
import com.xufeng.mvpkotlin.bean.TabEntity
import com.xufeng.mvpkotlin.ui.fragment.CategoryFragment
import com.xufeng.mvpkotlin.ui.fragment.HomeFragment
import com.xufeng.mvpkotlin.ui.fragment.HotFragment
import com.xufeng.mvpkotlin.ui.fragment.MineFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
class MainActivity : BaseActivity() {

    private val mTitles = arrayOf("每日精选", "分类", "热门", "我的")

    // 未被选中的图标
    private val mIconUnSelectIds = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    // 被选中的图标
    private val mIconSelectIds = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)

    private val mTabEntities = ArrayList<CustomTabEntity>()
    private var mHomeFragment: HomeFragment? = null
    private var mCategoryFragment: CategoryFragment? = null
    private var mHotFragment: HotFragment? = null
    private var mMeiTuFragment: MineFragment? = null

    //默认为0
    private var mIndex = 0

    private var mExitTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt("mTabIndex")
        }
        super.onCreate(savedInstanceState)
        initTab()
        tab_layout.currentTab = mIndex
        switchFragment(mIndex)
    }

    private fun initTab() {
        (0 until mTitles.size).mapTo(mTabEntities) {
            TabEntity(mTitles[it], mIconSelectIds[it], mIconUnSelectIds[it])
        }
        tab_layout.setTabData(mTabEntities)
        tab_layout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabReselect(position: Int) {
            }

            override fun onTabSelect(position: Int) {
                //切换Fragment
                switchFragment(position)
            }

        })
    }

    /**
     * 切换Fragment
     * @param position 下标
     */
    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {
            0 //首页
            -> if (mHomeFragment == null) {
                mHomeFragment = HomeFragment.getInstance(mTitles[position])
                transaction.add(R.id.fl_container, mHomeFragment, "home")
            } else {
                transaction.show(mHomeFragment)
            }
            1 //分类
            -> if (mCategoryFragment == null) {
                mCategoryFragment = CategoryFragment.getInstance(mTitles[position])
                transaction.add(R.id.fl_container, mCategoryFragment, "category")
            } else {
                transaction.show(mCategoryFragment)
            }
            2 //热门
            -> if (mHotFragment == null) {
                mHotFragment = HotFragment.getInstance(mTitles[position])
                transaction.add(R.id.fl_container, mHotFragment, "hot")
            } else {
                transaction.show(mHotFragment)
            }
            3 //更多
            -> if (mMeiTuFragment == null) {
                mMeiTuFragment = MineFragment.getInstance(mTitles[position])
                transaction.add(R.id.fl_container, mMeiTuFragment, "mei")
            } else {
                transaction.show(mMeiTuFragment)
            }
            else -> {
            }
        }
        mIndex = position
        tab_layout.currentTab = mIndex
        transaction.commitAllowingStateLoss()
    }

    /**
     * 隐藏所有的Fragment
     * @param transaction transaction
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        if (null != mHomeFragment) {
            transaction.hide(mHomeFragment)
        }
        if (null != mCategoryFragment) {
            transaction.hide(mCategoryFragment)
        }
        if (null != mHotFragment) {
            transaction.hide(mHotFragment)
        }
        if (null != mMeiTuFragment) {
            transaction.hide(mMeiTuFragment)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //记录fragment的位置,防止崩溃 activity被系统回收时，fragment错乱
        if (tab_layout != null) {
            outState.putInt("currTabIndex", mIndex)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                finish()
            } else {
                mExitTime = System.currentTimeMillis()
                toast("再按一次退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initData() {
    }

    override fun initView() {
    }

    override fun start() {

    }

}