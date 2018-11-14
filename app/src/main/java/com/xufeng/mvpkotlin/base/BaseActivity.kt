package com.xufeng.mvpkotlin.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.xufeng.multiplestatusview.MultipleStatusView
import org.jetbrains.anko.toast

/**
 * Ver 1.0, 18-9-20, xufeng, Create file
 */
abstract class BaseActivity : AppCompatActivity() {

    protected var mLayoutStatusView: MultipleStatusView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initData()
        initView()
        start()
        mLayoutStatusView?.setOnClickListener{
            toast("您点击了重试视图")
            start()
        }
    }

    abstract fun initData()

    abstract fun initView()

    /**
     * 开始请求
     */
    abstract fun start()

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    fun openKeyBoard(editText: EditText, context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    fun closeKeyBoard(editText: EditText, context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}