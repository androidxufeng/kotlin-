package com.xufeng.mvpkotlin.model

import com.xufeng.mvpkotlin.bean.CategoryBean
import com.xufeng.mvpkotlin.http.RetrofitManager
import io.reactivex.Observable

/**
 * Ver 1.0, 18-9-21, xufeng, Create file
 */
class CategoryModel {

    fun getCategoryData(): Observable<ArrayList<CategoryBean>> {
        return RetrofitManager.service.getCategory()
    }
}