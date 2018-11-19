/*
 * AboutActivity.kt
 *
 * Description
 *
 * Author xufeng
 *
 * Ver 1.0, 18-11-15, xufeng, Create file
 */

package com.xufeng.mvpkotlin.ui.activity

import android.content.Intent
import android.net.Uri
import com.xufeng.mvpkotlin.R
import com.xufeng.mvpkotlin.base.BaseActivity
import com.xufeng.mvpkotlin.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : BaseActivity() {
    override fun initData() {

    }

    override fun initView() {
        StatusBarUtils.darkMode(this)
        StatusBarUtils.setPaddingSmart(this, toolbar)

        toolbar.setNavigationOnClickListener { finish() }

        relayout_gitHub.setOnClickListener {
            val uri = Uri.parse("https://github.com/git-xuhao/KotlinMvp")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    override fun start() {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_about
    }
}