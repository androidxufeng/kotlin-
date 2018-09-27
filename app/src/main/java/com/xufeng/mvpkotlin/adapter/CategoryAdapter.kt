package com.xufeng.mvpkotlin.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.request.RequestOptions
import com.xufeng.mvpkotlin.R
import com.xufeng.mvpkotlin.app.Constant
import com.xufeng.mvpkotlin.app.MyApplication
import com.xufeng.mvpkotlin.bean.CategoryBean
import com.xufeng.mvpkotlin.glide.GlideApp
import com.xufeng.mvpkotlin.glide.GlideRoundTransform
import com.xufeng.mvpkotlin.ui.activity.CategoryDetailActivity


/**
 * Ver 1.0, 18-9-26, xufeng, Create file
 */
class CategoryAdapter(context: Context, categoryList: ArrayList<CategoryBean>, layoutId: Int) :
        CommonAdapter<CategoryBean>(context, categoryList, layoutId) {

    fun setData(categoryList: ArrayList<CategoryBean>) {
        mData.clear()
        mData = categoryList
        notifyDataSetChanged()
    }

    override fun bindData(holder: ViewHolder, data: CategoryBean, position: Int) {
        holder.setText(R.id.tv_category_name, "#${data.name}")
        //设置方正兰亭细黑简体
        holder.getView<TextView>(R.id.tv_category_name).typeface =
                Typeface.createFromAsset(MyApplication.context.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")

        holder.setImagePath(R.id.iv_category, object : ViewHolder.HolderImageLoader(data.bgPicture) {
            override fun loadImage(iv: ImageView, path: String) {
                GlideApp.with(MyApplication.context)
                        .load(path)
                        .apply(RequestOptions().placeholder(R.color.color_darker_gray)
                                .optionalTransform(GlideRoundTransform()))
                        .into(iv)
            }
        })

        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(mContext as Activity, CategoryDetailActivity::class.java)
                intent.putExtra(Constant.BUNDLE_CATEGORY_DATA, data)
                mContext.startActivity(intent)
            }

        })
    }
}