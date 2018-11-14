package com.xufeng.mvpkotlin.ui.presenter

import com.xufeng.mvpkotlin.base.BasePresenter
import com.xufeng.mvpkotlin.bean.HomeBean
import com.xufeng.mvpkotlin.http.exception.ExceptionHandle
import com.xufeng.mvpkotlin.model.HomeModel
import com.xufeng.mvpkotlin.rx.scheduler.SchedulerUtils
import com.xufeng.mvpkotlin.ui.contract.HomeContract

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {

    private var mHomeBannerBean: HomeBean? = null

    private var mNextPageUrl: String? = null

    private val mModel: HomeModel by lazy {
        HomeModel()
    }


    override fun requestHomeData(num: Int) {

        checkViewAttached()
        mView?.showLoading()

        val disposable = mModel.requesetBannerData(num)
                .flatMap { homeBean ->
                    //过滤掉 Banner2(包含广告,等无用的 Type), 具体查看接口分析
                    val bannerItemList = homeBean.issueList[0].itemList

                    bannerItemList.filter { item ->
                        item.type == "banner2" || item.type == "horizontalScrollCard"
                    }.forEach { item ->
                        bannerItemList.remove(item)
                    }
                    mHomeBannerBean = homeBean
                    mModel.loadMoreData(homeBean.nextPageUrl)
                }
                .compose(SchedulerUtils.ioToMain())
                .subscribe({ homeBean: HomeBean? ->
                    mView?.run {
                        if (homeBean != null) {
                            dismissLoading()
                            mNextPageUrl = homeBean.nextPageUrl
                            //过滤掉 Banner2(包含广告,等无用的 Type), 具体查看接口分析
                            val newBannerItemList = homeBean.issueList[0].itemList
                            newBannerItemList.filter { item ->
                                item.type == "banner2" || item.type == "horizontalScrollCard"
                            }.forEach { item ->
                                //移除 item
                                newBannerItemList.remove(item)
                            }
                            // 记录Banner 长度
                            mHomeBannerBean!!.issueList[0].count = mHomeBannerBean!!.issueList[0].itemList.size
                            //赋值过滤后的数据 + banner 数据
                            mHomeBannerBean?.issueList!![0].itemList.addAll(newBannerItemList)
                            showHomeData(mHomeBannerBean!!)
                        }
                    }
                }, { t ->
                    mView?.apply {
                        dismissLoading()
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }
                }
                )
        addSubscription(disposable)
    }

    override fun loadMoreData() {
        val disposable = mModel.loadMoreData(mNextPageUrl!!)
                .compose(SchedulerUtils.ioToMain())
                .subscribe({ homeBean: HomeBean ->
                    mView?.apply {
                        //过滤掉 Banner2(包含广告,等无用的 Type), 具体查看接口分析
                        val newItemList = homeBean.issueList[0].itemList
                        newItemList.filter { item ->
                            item.type == "banner2" || item.type == "horizontalScrollCard"
                        }.forEach { item ->
                            //移除 item
                            newItemList.remove(item)
                        }
                        mNextPageUrl = homeBean.nextPageUrl
                        setMoreData(newItemList)
                    }
                }, { t ->
                    mView?.apply {
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }
}