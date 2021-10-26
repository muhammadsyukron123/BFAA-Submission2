package com.syukron.githubusersubmission2.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.syukron.githubusersubmission2.Data.UserData
import com.syukron.githubusersubmission2.R
import com.syukron.githubusersubmission2.UserDetailActivity
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_row_user.view.*
import java.util.*
import kotlin.collections.ArrayList

lateinit var mContext: Context
var listUserData = ArrayList<UserData>()

class UserDataAdapter (private val listData: ArrayList<UserData>) : RecyclerView.Adapter<UserDataAdapter.ListDataHolder>(),
    Filterable {

    init {
        listUserData = listData
    }

    inner class ListDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgAvatar: CircleImageView = itemView.img_item_photo
        var name: TextView = itemView.tv_name
        var username: TextView = itemView.tv_username
        var company: TextView = itemView.tv_company
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_user, parent, false)
        val sch = ListDataHolder(view)
        mContext = parent.context
        return sch
    }

    override fun onBindViewHolder(holder: ListDataHolder, position: Int) {
        val data = listUserData[position]
        Glide.with(holder.itemView.context)
            .load(data.avatar)
            .apply(RequestOptions().override(250, 250))
            .into(holder.imgAvatar)
        holder.name.text = data.name
        holder.username.text = data.username
        holder.company.text = data.company

        holder.itemView.setOnClickListener {
            val dataUser = UserData(
                data.username,
                data.name,
                data.avatar,
                data.company,
                data.location,
                data.repository,
                data.followers,
                data.following
            )
            val intentDetail = Intent(mContext, UserDetailActivity::class.java)
            intentDetail.putExtra(UserDetailActivity.EXTRA_DATA, dataUser)
            mContext.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int {
        return listUserData.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charSearch = p0.toString()
                listUserData = if (charSearch.isEmpty()) {
                    listData
                } else {
                    val resultList = ArrayList<UserData>()
                    for (row in listUserData) {
                        if ((row.username.toString().toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT)))
                        ) {
                            resultList.add(
                                UserData(
                                    row.username,
                                    row.name,
                                    row.avatar,
                                    row.company,
                                    row.location,
                                    row.repository,
                                    row.followers,
                                    row.following
                                )
                            )
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = listUserData
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                listUserData = p1?.values as ArrayList<UserData>
                notifyDataSetChanged()
            }

        }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(userModel: UserData)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}