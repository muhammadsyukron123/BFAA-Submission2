package com.syukron.githubusersubmission2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.syukron.githubusersubmission2.Data.UserData
import com.syukron.githubusersubmission2.Adapter.UserFollowersAdapter
import com.syukron.githubusersubmission2.Adapter.listUserFollowers
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_user_followers.*
import org.json.JSONArray
import org.json.JSONObject


class UserFollowersFragment : Fragment() {
    companion object {
        private val TAG = UserFollowersFragment::class.java.simpleName
        const val EXTRA_DATA = "extra_data"
    }

    private var listData: ArrayList<UserData> = ArrayList()
    private lateinit var adapter: UserFollowersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UserFollowersAdapter(listData)
        listData.clear()
        val dataUser = activity?.intent?.getParcelableExtra<UserData>(EXTRA_DATA) as UserData
        getUserData(dataUser.username.toString())
    }

    private fun getUserData(id: String) {
        pbUserFollowers.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token YOUR_GITHUB_API_TOKEN")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$id/followers"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                pbUserFollowers.visibility = View.INVISIBLE
                val result = String(responseBody!!)
                Log.d(TAG, result)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username: String = jsonObject.getString("login")
                        getDataUserDetail(username)
                    }
                } catch (e: Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT)
                        .show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                pbUserFollowers.visibility = View.INVISIBLE
                val errorMessage = when(statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG)
                    .show()
            }

        })
    }

    private fun getDataUserDetail(id: String) {
        pbUserFollowers.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token YOUR_GITHUB_API_TOKEN")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$id"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                pbUserFollowers.visibility = View.INVISIBLE
                val result = String(responseBody!!)
                Log.d(TAG, result)
                try {
                    val jsonObject = JSONObject(result)
                    val username: String? = jsonObject.getString("login").toString()
                    val name: String? = jsonObject.getString("name").toString()
                    val avatar: String? = jsonObject.getString("avatar_url").toString()
                    val company: String? = jsonObject.getString("company").toString()
                    val location: String? = jsonObject.getString("location").toString()
                    val repository: Int = jsonObject.getInt("public_repos")
                    val followers: Int = jsonObject.getInt("followers")
                    val following: Int = jsonObject.getInt("following")
                    listData.add(
                        UserData(
                            username,
                            name,
                            avatar,
                            company,
                            location,
                            repository,
                            followers,
                            following
                        )
                    )
                    showRecyclerList()
                } catch (e: Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT)
                        .show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                pbUserFollowers.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun showRecyclerList() {
        rvUserFollowers.layoutManager = LinearLayoutManager(activity)
        val listDataAdapter =
            UserFollowersAdapter(listUserFollowers)
        rvUserFollowers.adapter = adapter

        listDataAdapter.setOnItemClickCallback(object :
            UserFollowersAdapter.OnItemClickCallback {
            override fun onItemClicked(DataFollowers: UserData) { }
        })
    }
}