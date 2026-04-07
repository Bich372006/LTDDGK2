package com.example.ltddgk.data.model

data class User(
    val id: String = "",
    val username: String = "",
    val password: String = "",
    val role: String = "",
    val avatarUrl: String = ""
) {
    // Hiển thị gọn
    fun displayName(): String {
        return "$username ($role)"
    }

    // Kiểm tra dữ liệu hợp lệ
    fun isValid(): Boolean {
        return username.isNotBlank() && password.isNotBlank()
    }
}