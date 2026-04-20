package com.example.ltddgk

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ltddgk.ui.admin.AdminDashboard
import com.example.ltddgk.ui.admin.AdminViewModel
import com.example.ltddgk.ui.auth.LoginScreen
import com.example.ltddgk.ui.auth.RegisterScreen
import com.google.firebase.auth.FirebaseAuth // 🔥 Thêm Firebase Auth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    // 🔥 KIỂM TRA TRẠNG THÁI ĐĂNG NHẬP THỰC TẾ
    // Nếu đã có user thì vào thẳng Dashboard, ngược lại thì vào Login
    val startDest = if (auth.currentUser != null) "admin_dashboard" else "login"

    NavHost(
        navController = navController,
        startDestination = startDest
    ) {
        // 1. Màn hình Đăng nhập
        composable("login") {
            LoginScreen(
                onNavigateBack = {
                    (context as? ComponentActivity)?.finish()
                },
                onForgotPassword = {
                    Toast.makeText(context, "Chức năng đang được cập nhật!", Toast.LENGTH_SHORT).show()
                },
                onLoginSuccess = {
                    // 🔥 Điều hướng và xóa sạch các màn hình trước đó trong Stack
                    navController.navigate("admin_dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }

        // 2. Màn hình Đăng ký
        composable("register") {
            RegisterScreen(
                navController = navController,
                onNavigateBack = { navController.popBackStack() },
                onRegisterSuccess = {
                    Toast.makeText(context, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }

        // 3. Màn hình Admin Dashboard
        composable("admin_dashboard") {
            val adminViewModel: AdminViewModel = viewModel()
            AdminDashboard(
                viewModel = adminViewModel,
                onLogout = {
                    // 🔥 Thực hiện đăng xuất Firebase trước khi điều hướng
                    auth.signOut()
                    navController.navigate("login") {
                        popUpTo("admin_dashboard") { inclusive = true }
                    }
                }
            )
        }
    }
}