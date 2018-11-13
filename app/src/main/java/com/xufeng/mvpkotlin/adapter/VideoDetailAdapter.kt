package com.xufeng.mvpkotlin.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.xufeng.mvpkotlin.R
import com.xufeng.mvpkotlin.app.MyApplication
import com.xufeng.mvpkotlin.app.durationFormat
import com.xufeng.mvpkotlin.bean.HomeBean
import com.xufeng.mvpkotlin.glide.GlideApp
import com.xufeng.mvpkotlin.glide.GlideRoundTransform
import com.xufeng.mvpkotlin.widget.ExpandableTextView

/**
 * Ver 1.0, 18-9-26, xufeng, Create file
 */
class VideoDetailAdapter(context: Context, data: ArrayList<HomeBean.Issue.Item>) :
        CommonAdapter<HomeBean.Issue.Item>(context, data, object : MultipleType<HomeBean.Issue.Item> {
            override fun getLayoutId(item: HomeBean.Issue.Item, position: Int): Int {
                return when {
                    position == 0 -> R.layout.item_video_info

                    data[position].type == "textCard" -> R.layout.item_video_text_card

                    data[position].type == "videoSmallCard" ->
                        R.layout.item_video_small_card

                    else -> throw IllegalAccessException("Api 解析出错了，出现其他类型")

                }
            }

        }
        ) {

    fun addData(item: HomeBean.Issue.Item) {
        mData.clear()
        notifyDataSetChanged()
        mData.add(item)
        notifyItemInserted(0)
    }

    /**
     * 添加相关推荐等数据 Item
     */
    fun addData(item: ArrayList<HomeBean.Issue.Item>) {
        mData.addAll(item)
        notifyItemRangeInserted(1, item.size)
    }

    /**
     * Kotlin的函数可以作为参数，写callback的时候，可以不用interface了
     */
    private var mOnItemClickRelatedVideo: ((item: HomeBean.Issue.Item) -> Unit)? = null

    fun setOnItemDetailClick(listener: (item: HomeBean.Issue.Item) -> Unit) {
        this.mOnItemClickRelatedVideo = listener
    }

    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        when {
            position == 0 -> setVideoDetailInfo(data, holder)

            data.type == "textCard" -> {
                holder.setText(R.id.tv_text_card, data.data?.text!!)
                //设置字体
                holder.getView<TextView>(R.id.tv_text_card).typeface = Typeface.createFromAsset(MyApplication.context.assets,
                        "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
            }

            data.type == "videoSmallCard" -> {
                holder.setText(R.id.tv_title, data.data?.title!!)
                holder.setText(R.id.tv_tag, "#${data.data.category} / ${durationFormat(data.data.duration)}")
                holder.setImagePath(R.id.iv_video_small_card, object : ViewHolder.HolderImageLoader(data.data.cover.detail) {
                    override fun loadImage(iv: ImageView, path: String) {
                        GlideApp.with(MyApplication.context)
                                .load(path)
                                .optionalTransform(GlideRoundTransform())
                                .into(iv)
                    }
                })
                // 判断onItemClickRelatedVideo 并使用
                holder.itemView.setOnClickListener { mOnItemClickRelatedVideo?.invoke(data) }
            }
            else -> throw IllegalAccessException("Api 解析出错了，出现其他类型")
        }
    }

    private fun setVideoDetailInfo(data: HomeBean.Issue.Item, holder: ViewHolder) {
        data.data?.title?.let {
            holder.setText(R.id.tv_title, it)
        }
        with(holder) {
            getView<ExpandableTextView>(R.id.expand_text_view).text = data.data?.description
            //标签
            setText(R.id.tv_tag, "#${data.data?.category} / ${durationFormat(data.data?.duration)}")
            //喜欢
            setText(R.id.tv_action_favorites, data.data?.consumption?.collectionCount.toString())
            //分享
            setText(R.id.tv_action_share, data.data?.consumption?.shareCount.toString())
            //评论
            setText(R.id.tv_action_reply, data.data?.consumption?.replyCount.toString())
        }

        if (data.data?.author != null) {
            holder.setText(R.id.tv_author_name, data.data.author.name)
            holder.setText(R.id.tv_author_desc, data.data.author.description)
            holder.setImagePath(R.id.iv_avatar, object : ViewHolder.HolderImageLoader(data.data.author.icon) {
                override fun loadImage(iv: ImageView, path: String) {
                    //加载头像
                    GlideApp.with(MyApplication.context)
                            .load(path)
                            .placeholder(R.drawable.default_avatar).circleCrop()
                            .into(iv)
                }
            })
        } else {
            holder.setViewVisibility(R.id.layout_author_view, View.GONE)
        }

        with(holder) {
            getView<TextView>(R.id.tv_action_favorites).setOnClickListener {
                Toast.makeText(MyApplication.context, "喜欢", Toast.LENGTH_SHORT).show()
            }
            getView<TextView>(R.id.tv_action_share).setOnClickListener {
                Toast.makeText(MyApplication.context, "分享", Toast.LENGTH_SHORT).show()
            }
            getView<TextView>(R.id.tv_action_reply).setOnClickListener {
                Toast.makeText(MyApplication.context, "评论", Toast.LENGTH_SHORT).show()
            }
        }
    }

}