package com.example.ltddgk.ui.auth

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ltddgk.ui.admin.AdminDashboard
import com.example.ltddgk.ui.admin.AdminViewModel

@Composable
fun AuthScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // 1. Màn hình Đăng Nhập
        composable("login") {
            LoginScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onForgotPassword = {
                    // Bích có thể thêm logic quên mật khẩu ở đây sau
                },
                onLoginSuccess = {
                    // Chuyển sang Dashboard và xóa lịch sử màn hình Login
                    navController.navigate("admin_dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }

        // 2. Màn hình Đăng Ký
        composable("register") {
            RegisterScreen(
                navController = navController, // 🔥 Tham số quan trọng để hết lỗi đỏ
                onNavigateBack = {
                    navController.popBackStack()
                },
                onRegisterSuccess = {
                    // Đăng ký xong quay về màn hình Login
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }

        // 3. Màn hình Quản Lý (Admin)
        composable("admin_dashboard") {
            val adminViewModel: AdminViewModel = viewModel()
            AdminDashboard(
                viewModel = adminViewModel,
                onLogout = {
                    // Đăng xuất và quay về màn hình Login
                    navController.navigate("login") {
                        popUpTo("admin_dashboard") { inclusive = true }
                    }
                }
            )
        }
    }
}