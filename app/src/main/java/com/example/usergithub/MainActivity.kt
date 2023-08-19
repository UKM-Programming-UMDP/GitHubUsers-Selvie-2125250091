package com.example.usergithub

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.usergithub.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var nightMODE: Boolean = false
    private lateinit var sp : SharedPreferences
    private lateinit var spE : SharedPreferences.Editor
    private var isClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvList.layoutManager = LinearLayoutManager(this)

        binding.pbLoading.visibility = View.VISIBLE

        sp = getSharedPreferences("MODE", Context.MODE_PRIVATE)
        nightMODE = sp.getBoolean("night", false)

        if (nightMODE) {
            binding.fabThemeMode.setImageResource(R.drawable.ic_dark_mode)
        }

        binding.fabThemeMode.setOnClickListener(View.OnClickListener {
            if (nightMODE) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                spE = sp.edit()
                spE.putBoolean("night", false)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                spE = sp.edit()
                spE.putBoolean("night", true)
            }
            spE.apply()
        })

        fetchAllUsers()
    }

    private fun fetchAllUsers() {
        val client = ApiConfig.provideApiService()
        client.getListUser().enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(call: Call<List<UserResponse>>, response: Response<List<UserResponse>>) {
                if (response.isSuccessful) {
                    val userList = response.body() ?: emptyList()
                    fetchAndPopulateUserDetails(userList)
                    Log.d("Response Success", "abc")
                } else {
                    // Handle response error
                    Log.d("Response Error", "abc")
                    binding.pbLoading.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                // Handle network failure
                binding.pbLoading.visibility = View.GONE
            }
        })
    }

    private fun fetchAndPopulateUserDetails(userList: List<UserResponse>) {
        val updatedUserList = mutableListOf<UserResponse>()
        val client = ApiConfig.provideApiService()

        var usersProcessed = 0
        for (user in userList) {
            user.login?.let {
                client.getUserDetailsByUsername(it).enqueue(object : Callback<UserResponse> {
                    override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                        if (response.isSuccessful) {
                            val detailedUser = response.body()
                            detailedUser?.let {
                                updatedUserList.add(it)
                            }
                        }

                        usersProcessed++
                        if (usersProcessed == userList.size) {
                            updateRecyclerView(updatedUserList)
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        // Handle individual user details fetch failure
                        usersProcessed++
                        if (usersProcessed == userList.size) {
                            updateRecyclerView(updatedUserList)
                        }
                    }
                })
            }
        }
    }

    private fun updateRecyclerView(users: List<UserResponse>) {
        binding.pbLoading.visibility = View.GONE
        val adapter = UsersAdapter(this)
        adapter.submitList(users)
        binding.rvList.adapter = adapter
    }
}
