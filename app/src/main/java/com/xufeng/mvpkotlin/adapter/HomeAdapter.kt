package com.xufeng.mvpkotlin.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.xufeng.mvpkotlin.R
import com.xufeng.mvpkotlin.app.Constant
import com.xufeng.mvpkotlin.app.durationFormat
import com.xufeng.mvpkotlin.bean.HomeBean
import com.xufeng.mvpkotlin.glide.GlideApp
import com.xufeng.mvpkotlin.ui.activity.VideoDetailActivity
import io.reactivex.Observable

/**
 * Ver 1.0, 18-9-25, xufeng, Create file
 */
class HomeAdapter(context: Context, data: ArrayList<HomeBean.Issue.Item>) : CommonAdapter<HomeBean.Issue.Item>(context, data, -1) {

    // banner 作为 RecyclerView 的第一项
    var bannerItemSize = 0

    companion object {
        private val ITEM_TYPE_BANNER = 1
        private val ITEM_TYPE_TEXT_HEADER = 2   //textHeader
        private val ITEM_TYPE_CONTENT = 3
    }

    fun setBanner(count: Int) {
        bannerItemSize = count
    }

    /**
     * 添加更多数据
     */
    fun addItemData(itemList: ArrayList<HomeBean.Issue.Item>) {
        mData.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> ITEM_TYPE_BANNER
            mData[position + bannerItemSize - 1].type == "textHeader" ->
                ITEM_TYPE_TEXT_HEADER
            else ->
                ITEM_TYPE_CONTENT
        }
    }

    override fun getItemCount(): Int {
        val count = when {
            mData.size > bannerItemSize -> mData.size - bannerItemSize + 1
            mData.isEmpty() -> 0
            else -> 1
        }
        return count
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_TYPE_BANNER -> ViewHolder(inflaterView(R.layout.item_home_banner, parent))
            ITEM_TYPE_TEXT_HEADER -> ViewHolder(inflaterView(R.layout.item_home_header, parent))
            else -> ViewHolder(inflaterView(R.layout.item_home_content, parent))
        }
    }

    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        when (getItemViewType(position)) {
            ITEM_TYPE_BANNER -> {
                val bannerListData: ArrayList<HomeBean.Issue.Item> = mData.take(bannerItemSize).toCollection(ArrayList())

                val bannerFeedList = ArrayList<String>()
                val bannerTitleList = ArrayList<String>()

                Observable.fromIterable(bannerListData)
                        .subscribe{ list ->
                            bannerTitleList.add(list.data!!.title)
                            bannerFeedList.add(list.data.cover.feed)
                        }

                val banner = holder.getView<BGABanner>(R.id.banner)
                banner.run {
                    setAutoPlayAble(bannerItemSize > 1)
                    setData(bannerFeedList, bannerTitleList)
                    setAdapter { _, _, feedImageUrl, pos ->
                        GlideApp.with(mContext!!)
                                .load(feedImageUrl)
                                .placeholder(R.drawable.default_avatar)
                                .into(banner.getItemImageView(pos))
                    }
                }

                //没有使用到的参数在 kotlin 中用"_"代替
                banner.setDelegate { _, imageView, _, i ->

                    goToVideoPlayer(mContext as Activity, imageView, bannerListData[i])

                }
            }

            ITEM_TYPE_TEXT_HEADER -> holder.setText(R.id.tvHeader, mData[position + bannerItemSize - 1].data?.text!!)

            ITEM_TYPE_CONTENT -> {
                videoItem(holder, mData[position + bannerItemSize - 1])
            }
        }
    }

    private fun videoItem(holder: ViewHolder, item: HomeBean.Issue.Item) {
        val data = item.data
        val feed = data?.cover?.feed
        var avatar = data?.author?.icon
        val defAvatar = R.drawable.default_avatar
        var tagText: String? = "#"

        // 作者出处为空，就显获取提供者的信息
        if (avatar.isNullOrEmpty()) {
            avatar = data?.provider?.icon
        }
        // 加载封页图
        GlideApp.with(mContext!!).load(feed).into(holder.getView(R.id.iv_cover_feed))

        if (avatar.isNullOrEmpty()) {
            GlideApp.with(mContext!!).load(defAvatar).into(holder.getView(R.id.iv_avatar))
        } else {
            GlideApp.with(mContext!!).load(avatar).into(holder.getView(R.id.iv_avatar))
        }

        holder.setText(R.id.tv_title, data?.title!!)

        //遍历标签
        data.tags.take(4).forEach {
            tagText += it.name + "/"
        }

        val duration = durationFormat(data.duration)

        tagText += duration

        holder.setText(R.id.tv_tag, tagText!!)

        holder.setText(R.id.tv_category, "#" + data.category)

        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                goToVideoPlayer(mContext as Activity, holder.getView<ImageView>(R.id.iv_cover_feed), item)
            }
        })
    }

    /**
     * 加载布局
     */
    private fun inflaterView(mLayoutId: Int, parent: ViewGroup): View {
        //创建view
        val view = mInflate?.inflate(mLayoutId, parent, false)
        return view!!
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
            val pair = Pair<View, String>(view, VideoDetailActivity.Companion.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, pair)
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }
}