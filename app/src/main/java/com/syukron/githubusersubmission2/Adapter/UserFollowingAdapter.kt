package com.syukron.githubusersubmission2.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.syukron.githubusersubmission2.Data.UserData
import com.syukron.githubusersubmission2.R
import kotlinx.android.synthetic.main.item_row_user.view.*

var listUserFollowing = ArrayList<UserData>()

class UserFollowingAdapter (listData: ArrayList<UserData>) : RecyclerView.Adapter<UserFollowingAdapter.ListDataHolder>() {

    init {
        listUserFollowing = listData
    }

    inner class ListDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgAvatar: ImageView = itemView.img_item_photo
        var name: TextView = itemView.tv_name
        var username: TextView = itemView.tv_username
        var company: TextView = itemView.tv_company
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(DataFollowing: UserFollowingAdapter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_user, parent, false)
        val sch = ListDataHolder(view)
        mContext = parent.context
        return sch
    }

    override fun onBindViewHolder(holder: ListDataHolder, position: Int) {
        val data = listUserFollowing[position]
        Glide.with(holder.itemView.context)
            .load(data.avatar)
            .apply(RequestOptions().override(250, 250))
            .into(holder.imgAvatar)
        holder.name.text = data.name
        holder.username.text = data.username
        holder.company.text = data.company

        holder.itemView.setOnClickListener { }
    }

    override fun getItemCount(): Int {
        return listUserFollowing.size
    }
}