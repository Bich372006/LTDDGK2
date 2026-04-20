package com.example.ltddgk.data.model

// Sử dụng mặc định rỗng cho tất cả các trường để tránh lỗi Mapping
data class User(
    val id: String = "",
    val name: String = "",
    val role: String = "",
    val password:String= "",
    val avatarUrl: String = ""
)