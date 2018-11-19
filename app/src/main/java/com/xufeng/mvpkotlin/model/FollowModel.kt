/*
 * Copyright (C), 2018, TP-LINK TECHNOLOGIES CO., LTD.
 *
 * ${FILE_NAME}
 *
 * Description
 *
 * Author xufeng
 *
 * Ver 1.0, 18-11-15, xufeng, Create file
 */

package com.xufeng.mvpkotlin.model

import com.xufeng.mvpkotlin.bean.HomeBean
import com.xufeng.mvpkotlin.http.RetrofitManager
import io.reactivex.Observable

class FollowModel {

    fun requestFollowList(): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getFollowInfo()
    }

    fun loadMoreData(url: String): Observable<HomeBean.Issue> {

        return RetrofitManager.service.getIssueData(url)
    }
}