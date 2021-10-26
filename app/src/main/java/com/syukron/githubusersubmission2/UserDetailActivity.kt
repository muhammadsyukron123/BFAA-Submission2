package com.syukron.githubusersubmission2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.syukron.githubusersubmission2.Data.UserData
import com.syukron.githubusersubmission2.Adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_user_detail.*

class UserDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        pbUserDetail.visibility = View.VISIBLE

        setData()
        configViewPager()
    }

    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            supportActionBar!!.title = title
        }
    }

    private fun configViewPager() {
        val viewPagerDetailAdapter = ViewPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = viewPagerDetailAdapter
        tabLayout.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f
    }

    private fun setData() {
        val dataUser = intent.getParcelableExtra<UserData>(EXTRA_DATA) as UserData
        setActionBarTitle(dataUser.name.toString())
        detail_name.text = dataUser.name.toString()
        detail_username.text = dataUser.username.toString()
        detail_company.text = dataUser.company.toString()
        detail_location.text = dataUser.location.toString()
        detail_repository.text = dataUser.repository.toString()
        detail_followers.text = dataUser.followers.toString()
        detail_following.text = dataUser.following.toString()
        Glide.with(this)
            .load(dataUser.avatar.toString())
            .into(detail_avatar)
    }
}