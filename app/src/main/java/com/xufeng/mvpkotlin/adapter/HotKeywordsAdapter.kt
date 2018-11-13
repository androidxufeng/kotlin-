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
package com.xufeng.mvpkotlin.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayoutManager
import com.xufeng.mvpkotlin.R

class HotKeywordsAdapter constructor(context: Context, list: ArrayList<String>, layoutID: Int) :
        CommonAdapter<String>(context, list, layoutID) {

    private var mOnTagItemClickListener: ((tag: String) -> Unit)? = null


    public fun setTagItemClickListener(onTagItemClickListener: (tag: String) -> Unit) {
        mOnTagItemClickListener = onTagItemClickListener
    }

    override fun bindData(holder: ViewHolder, data: String, position: Int) {
        holder.setText(R.id.tv_title, data)
        val params = holder.getView<TextView>(R.id.tv_title).layoutParams
        if (params is FlexboxLayoutManager.LayoutParams) {
            params.flexGrow = 1.0f
        }
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                mOnTagItemClickListener?.invoke(data)
            }
        })
    }
}