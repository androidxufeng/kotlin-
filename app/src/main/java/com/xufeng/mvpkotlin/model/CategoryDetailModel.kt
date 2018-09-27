package com.xufeng.mvpkotlin.model

import com.xufeng.mvpkotlin.bean.HomeBean
import com.xufeng.mvpkotlin.http.RetrofitManager
import io.reactivex.Observable

/**
 * Ver 1.0, 18-9-21, xufeng, Create file
 */
class CategoryDetailModel {

    fun getCategoryDetailList(id: Long): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getCategoryDetailList(id)
    }

    fun loadMoreData(url: String): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getIssueData(url)
    }
}