package com.xufeng.mvpkotlin.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import cn.bingoogolapple.bgabanner.BGABanner
import com.xufeng.mvpkotlin.R
import com.xufeng.mvpkotlin.bean.HomeBean
import com.xufeng.mvpkotlin.glide.GlideApp
import io.reactivex.Observable

/**
 * Ver 1.0, 18-9-25, xufeng, Create file
 */
class HomeAdapter(mContext: Context, mData: List<HomeBean.Issue.Item>) : CommonAdapter<HomeBean.Issue.Item>(mContext, mData, -1) {

    private var mContext: Context? = null

    // banner 作为 RecyclerView 的第一项
    private var bannerItemSize = 0

    init {
        this.mContext = mContext
    }

    companion object {
        private val ITEM_TYPE_BANNER = 1
        private val ITEM_TYPE_CONTENT = 2
    }

    fun setBanner(count: Int) {
        bannerItemSize = count
    }

    override fun getItemViewType(position: Int): Int {
        if (0 == position) {
            return ITEM_TYPE_BANNER
        }
        return ITEM_TYPE_CONTENT
    }

    override fun getItemCount(): Int {
        val count = when {
            mData.size > bannerItemSize -> mData.size - bannerItemSize + 1
            mData.isEmpty() -> 0
            else -> 1
        }
        Log.d("ceshi", "count = $count")
        return count
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_TYPE_BANNER -> ViewHolder(inflaterView(R.layout.item_home_banner, parent))
            ITEM_TYPE_CONTENT -> ViewHolder(inflaterView(R.layout.item_home_content, parent))
            else -> ViewHolder(inflaterView(R.layout.item_home_content, parent))
        }
    }

    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        when (getItemViewType(position)) {
            ITEM_TYPE_BANNER -> {
                var bannerListData: ArrayList<HomeBean.Issue.Item> = mData.take(bannerItemSize).toCollection(ArrayList())

                var bannerFeedList: ArrayList<String> = ArrayList()
                var bannerTitleList: ArrayList<String> = ArrayList()

                Observable.fromIterable(bannerListData)
                        .subscribe({ list ->
                            bannerTitleList.add(list.data!!.title)
                            bannerFeedList.add(list.data.cover.feed)
                        })

                val banner = holder.getView<BGABanner>(R.id.banner)
                banner.setAutoPlayAble(bannerItemSize > 1)
                banner.setData(bannerFeedList, bannerTitleList)
                /*banner.setAdapter(object : BGABanner.Adapter<ImageView, String> {
                    override fun fillBannerItem(banner: BGABanner?, itemView: ImageView?, feedImageUrl: String?, position: Int) {
                        Glide.with(mContext!!)
                                .load(feedImageUrl)
                                .into(itemView)
                    }

                })*/
                banner.setAdapter { _, _, feedImageUrl, pos ->
                    GlideApp.with(mContext!!)
                            .load(feedImageUrl)
                            .into(banner.getItemImageView(pos))
                }
            }
        }
    }

    /**
     * 加载布局
     */
    private fun inflaterView(mLayoutId: Int, parent: ViewGroup): View {
        //创建view
        val view = mInflate?.inflate(mLayoutId, parent, false)
        return view!!
    }
}