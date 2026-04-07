package com.example.ltddgk // Hoặc package com.example.ltddgk.ui.auth tùy vị trí file này

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// QUAN TRỌNG: Thêm 2 dòng import này để hết lỗi đỏ
import com.example.ltddgk.ui.auth.LoginScreen
import com.example.ltddgk.ui.auth.RegisterScreen

@Composable
fun AuthScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // Màn hình Đăng nhập
        composable("login") {
            LoginScreen(navController = navController)
        }

        // Màn hình Đăng ký
        composable("register") {
            RegisterScreen(navController = navController)
        }
    }
}