package com.example.ltddgk.ui.auth // Đảm bảo package khớp với thư mục của bạn

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    // Sử dụng state để lưu giá trị nhập vào
    var email by remember { mutableStateOf("bich030706@gmail.com") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val primaryPink = Color(0xFFFF69B4) // Màu hồng chủ đạo

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "ĐĂNG KÝ TÀI KHOẢN",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = primaryPink
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Trường Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryPink,
                focusedLabelColor = primaryPink
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Trường Mật khẩu
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mật khẩu") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryPink,
                focusedLabelColor = primaryPink
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Trường Xác nhận mật khẩu
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Xác nhận mật khẩu") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryPink,
                focusedLabelColor = primaryPink
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Nút Đăng ký
        Button(
            onClick = {
                // Kiểm tra điều kiện đăng ký
                if (password == confirmPassword && password.isNotEmpty()) {
                    // Sau khi đăng ký thành công, quay trở lại màn hình đăng nhập
                    navController.popBackStack()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryPink),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text(text = "ĐĂNG KÝ", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nút Quay lại
        TextButton(onClick = { navController.popBackStack() }) {
            Text(text = "Quay lại đăng nhập", color = Color.Gray)
        }
    }
}