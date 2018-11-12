package com.xufeng.mvpkotlin.bean

/**
 * Ver 1.0, 18-9-27, xufeng, Create file
 */
data class TabInfoBean(val tabInfo: TabInfo) {
    data class TabInfo(val tabList: ArrayList<Tab>)
    data class Tab(val id: Long, val name: String, val apiUrl: String)
}