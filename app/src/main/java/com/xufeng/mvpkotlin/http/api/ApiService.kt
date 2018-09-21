package com.xufeng.mvpkotlin.http.api

import com.xufeng.mvpkotlin.bean.FuckGoods
import com.xufeng.mvpkotlin.http.ResponseResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
interface ApiService {

    @GET("data/Android/10/{page}")
    fun getAndroidData(@Path("page") page: Int): Observable<ResponseResult<List<FuckGoods>>>
}