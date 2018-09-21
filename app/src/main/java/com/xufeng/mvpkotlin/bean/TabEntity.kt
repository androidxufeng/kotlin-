package com.xufeng.mvpkotlin.bean

import com.flyco.tablayout.listener.CustomTabEntity

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
class TabEntity(
        var title: String,
        private var selectedIcon: Int,
        private var unSelectedIcon: Int) : CustomTabEntity {
    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabTitle(): String {
        return title
    }
}