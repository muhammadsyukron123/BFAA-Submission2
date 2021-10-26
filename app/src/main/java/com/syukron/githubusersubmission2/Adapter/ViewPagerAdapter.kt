package com.syukron.githubusersubmission2.Adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.syukron.githubusersubmission2.R
import com.syukron.githubusersubmission2.UserFollowersFragment
import com.syukron.githubusersubmission2.UserFollowingFragment

class ViewPagerAdapter (private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val pages = listOf(UserFollowersFragment(), UserFollowingFragment())

    @StringRes
    private val tabTitle = intArrayOf(
        R.string.followers,
        R.string.following
    )
    override fun getCount(): Int {
        return pages.size
    }

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(tabTitle[position])
    }
}