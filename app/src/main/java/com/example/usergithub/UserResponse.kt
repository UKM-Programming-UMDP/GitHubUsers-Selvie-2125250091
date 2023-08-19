package com.example.usergithub

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("company")
    val company: String? = null,

    @field:SerializedName("blog")
    val blog: String? = null,

    @field:SerializedName("location")
    val location: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("public_repos")
    val publicRepos: Int? = null,

    @field:SerializedName("followers")
    val followers: Int? = null,

    @field:SerializedName("following")
    val following: Int? = null
)