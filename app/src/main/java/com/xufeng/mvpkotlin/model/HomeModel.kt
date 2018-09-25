package com.xufeng.mvpkotlin.model

import com.xufeng.mvpkotlin.bean.HomeBean
import com.xufeng.mvpkotlin.http.RetrofitManager
import io.reactivex.Observable

/**
 * Ver 1.0, 18-9-21, xufeng, Create file
 */
class HomeModel {

    fun requesetBannerData(num: Int): Observable<HomeBean> {
        return RetrofitManager.service.getFirstHomeData(num)
    }

    fun loadMoreData(url: String): Observable<HomeBean> {
        return RetrofitManager.service.getMoreHomeData(url)
    }
}