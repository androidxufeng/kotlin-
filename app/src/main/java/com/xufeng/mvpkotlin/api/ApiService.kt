package com.xufeng.mvpkotlin.api

import com.xufeng.mvpkotlin.bean.HomeBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
interface ApiService {

    /**
     * 首页精选
     */
    @GET("v2/feed?")
    fun getFirstHomeData(@Query("num") num: Int): Observable<HomeBean>

    /**
     * 根据 nextPageUrl 请求数据下一页数据
     */
    @GET
    fun getMoreHomeData(@Url url: String): Observable<HomeBean>

    /**
     * issue里面包了itemlist和nextpageurl
     */
    @GET
    fun getIssue(@Url url: String): Observable<HomeBean.Issue>

    /**
     * 根据item id获取相关视频
     */
    @GET("v4/video/related?")
    fun getRelatedData(@Query("id") id: Long): Observable<HomeBean.Issue>
}