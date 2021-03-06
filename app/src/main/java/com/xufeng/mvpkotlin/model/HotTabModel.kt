package com.xufeng.mvpkotlin.model

import com.xufeng.mvpkotlin.bean.TabInfoBean
import com.xufeng.mvpkotlin.http.RetrofitManager
import io.reactivex.Observable

/**
 * Ver 1.0, 18-9-21, xufeng, Create file
 */
class HotTabModel {

    fun getTabInfo(): Observable<TabInfoBean> {
        return RetrofitManager.service.getRankList()
    }

}