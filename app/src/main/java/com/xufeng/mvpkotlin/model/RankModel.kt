/*
 * Copyright (C), 2018, TP-LINK TECHNOLOGIES CO., LTD.
 *
 * ${FILE_NAME}
 *
 * Description
 *
 * Author xufeng
 *
 * Ver 1.0, 18-11-12, xufeng, Create file
 */

package com.xufeng.mvpkotlin.model

import com.xufeng.mvpkotlin.bean.HomeBean
import com.xufeng.mvpkotlin.http.RetrofitManager
import io.reactivex.Observable

class RankModel {

    fun requesetRankList(url: String): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getIssueData(url)
    }
}