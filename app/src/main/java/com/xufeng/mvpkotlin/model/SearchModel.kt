package com.xufeng.mvpkotlin.model

import com.xufeng.mvpkotlin.bean.HomeBean
import com.xufeng.mvpkotlin.http.RetrofitManager
import io.reactivex.Observable

/**
 * Ver 1.0, 18-9-21, xufeng, Create file
 */
class SearchModel {

    fun requestHotWordData(): Observable<ArrayList<String>> {
        return RetrofitManager.service.getHotWord()
    }

    fun getSearchResult(data: String): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getSearchData(data)
    }

    fun loadMoreData(url: String): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getIssueData(url)
    }

}