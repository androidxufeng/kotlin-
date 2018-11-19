package com.xufeng.mvpkotlin.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.xufeng.mvpkotlin.R
import com.xufeng.mvpkotlin.app.Constant
import com.xufeng.mvpkotlin.app.durationFormat
import com.xufeng.mvpkotlin.bean.HomeBean
import com.xufeng.mvpkotlin.glide.GlideApp
import com.xufeng.mvpkotlin.ui.activity.VideoDetailActivity

/*
 * Description
 *
 * Author xufeng
 *
 * Ver 1.0, 18-11-15, xufeng, Create file
 */
class FollowHorizontalAdapter(context: Context, categoryList: ArrayList<HomeBean.Issue.Item>, layoutId: Int) :
        CommonAdapter<HomeBean.Issue.Item>(context, categoryList, layoutId) {
    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        val horizontalItemData = data.data
        holder.setImagePath(R.id.iv_cover_feed, object : ViewHolder.HolderImageLoader(data.data?.cover?.feed!!) {
            override fun loadImage(iv: ImageView, path: String) {
                // 加载封页图
                GlideApp.with(mContext)
                        .load(path)
                        .into(holder.getView(R.id.iv_cover_feed))
            }
        })
        //横向 RecyclerView 封页图下面标题
        holder.setText(R.id.tv_title, horizontalItemData?.title!!)
        // 格式化时间
        val timeFormat = durationFormat(horizontalItemData.duration)
        if (data.data.tags.isNotEmpty()) {
            //标签
            holder.setText(R.id.tv_tag, "#${data.data.tags[0].name} / $timeFormat")
        }
        holder.setOnItemClickListener(listener = View.OnClickListener {
            goToVideoPlayer(mContext as Activity, holder.getView(R.id.iv_cover_feed), data)
        })
    }

    /**
     * 跳转到视频详情页面播放
     *
     * @param activity
     * @param view
     */
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(Constant.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.Companion.TRANSITION, true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair: Pair<View, String> = Pair(view, VideoDetailActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, pair)
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }
}