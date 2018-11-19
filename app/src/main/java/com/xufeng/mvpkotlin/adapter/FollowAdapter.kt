/*
 *
 * FollowAdapter.kt
 *
 * Description
 *
 * Author xufeng
 *
 * Ver 1.0, 18-11-15, xufeng, Create file
 */

package com.xufeng.mvpkotlin.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.xufeng.mvpkotlin.R
import com.xufeng.mvpkotlin.bean.HomeBean
import com.xufeng.mvpkotlin.glide.GlideApp

class FollowAdapter(context: Context, dataList: ArrayList<HomeBean.Issue.Item>) :
        CommonAdapter<HomeBean.Issue.Item>(context, dataList, object : MultipleType<HomeBean.Issue.Item> {
            override fun getLayoutId(item: HomeBean.Issue.Item, position: Int): Int {
                return when {
                    item.type == "videoCollectionWithBrief" ->
                        R.layout.item_follow
                    else ->
                        throw IllegalAccessException("Api 解析出错了，出现其他类型")
                }
            }
        }) {
    fun addData(dataList: ArrayList<HomeBean.Issue.Item>) {
        this.mData.addAll(dataList)
        notifyDataSetChanged()
    }

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        when {
            data.type == "videoCollectionWithBrief" -> setAuthorInfo(data, holder)
        }
    }

    /**
     * 加载作者信息
     */
    private fun setAuthorInfo(item: HomeBean.Issue.Item, holder: ViewHolder) {
        val headerData = item.data?.header
        /**
         * 加载作者头像
         */
        holder.setImagePath(R.id.iv_avatar, object : ViewHolder.HolderImageLoader(headerData?.icon!!) {
            override fun loadImage(iv: ImageView, path: String) {
                GlideApp.with(mContext)
                        .load(path)
                        .placeholder(R.drawable.default_avatar).circleCrop()
                        .into(holder.getView(R.id.iv_avatar))
            }
        })
        holder.setText(R.id.tv_title, headerData.title)
        holder.setText(R.id.tv_desc, headerData.description)
        val recyclerView = holder.getView<RecyclerView>(R.id.fl_recyclerView)
        //设置水平显示
        recyclerView.layoutManager = LinearLayoutManager(mContext as Activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = FollowHorizontalAdapter(mContext, item.data.itemList, R.layout.item_follow_horizontal)
    }

}