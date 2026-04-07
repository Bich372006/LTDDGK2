package com.example.ltddgk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ltddgk.ui.auth.LoginScreen
import com.example.ltddgk.ui.auth.RegisterScreen
import com.example.ltddgk.ui.admin.AdminDashboard
import com.example.ltddgk.ui.admin.AdminViewModel
import com.example.ltddgk.ui.theme.LTDDGKTheme // Import theme của bạn

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LTDDGKTheme { // Sử dụng theme để đồng bộ giao diện
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("register") {
            RegisterScreen(navController = navController)
        }
        composable("admin_dashboard") {
            // Khởi tạo ViewModel cho màn hình Admin
            val adminViewModel: AdminViewModel = viewModel()
            AdminDashboard(viewModel = adminViewModel)
        }
    }
}