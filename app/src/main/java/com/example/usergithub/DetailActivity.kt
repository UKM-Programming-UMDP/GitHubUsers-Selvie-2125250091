package com.example.usergithub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.usergithub.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")

        Log.d("intenttesting", username.toString())

        val client = ApiConfig.provideApiService()
        if (username != null) {
            client.getUserDetailsByUsername(username).enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if (response.isSuccessful) {
                        val followers: String? = response.body()?.followers.toString()
                        val following: String? = response.body()?.following.toString()
                        val repository: String? = response.body()?.publicRepos.toString()

                        binding.tvName.setText(response.body()?.name ?: "Name not available")
                        binding.tvUsername.setText(response.body()?.login ?: "Name not available")
                        binding.tvCompany.setText(response.body()?.company ?: "Name not available")
                        binding.tvLocation.setText(response.body()?.location ?: "Name not available")
                        binding.tvThelink.setText(response.body()?.blog ?: "Name not available")
                        binding.tvFollowers.setText(followers)
                        binding.tvFollowing.setText(following)
                        binding.tvRepository.setText(repository)
                        Glide.with(this@DetailActivity).load(response.body()?.avatarUrl).into(binding.ivProfpic)
                    } else {
                        // Handle response error
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    // Handle network failure
                }
            })
        }
    }
}