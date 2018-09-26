package com.xufeng.mvpkotlin.model

import com.xufeng.mvpkotlin.bean.HomeBean
import com.xufeng.mvpkotlin.http.RetrofitManager
import io.reactivex.Observable

/**
 * Ver 1.0, 18-9-26, xufeng, Create file
 */
class VideoDetailModel {

    fun requestRelatedData(id: Long): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getRelatedData(id)
    }
}