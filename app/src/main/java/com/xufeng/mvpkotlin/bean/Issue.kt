package com.xufeng.mvpkotlin.bean

/**
 * Ver 1.0, 18-9-21, xufeng, Create file
 *
 * "releaseTime": 1503190800000,
 * "type": "morning",
 * "date": 1503190800000,
 * "publishTime": 1503190800000,
 * "itemList": [],
 * "count": 5
 */

data class Issue(val releaseTime: Long,
                 val type: String,
                 val date: Long,
                 val total: Int,
                 val publishTime: Long,
                 val itemList: ArrayList<Item>,
                 var count: Int,
                 val nextPageUrl: String)

