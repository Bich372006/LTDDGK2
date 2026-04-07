package com.example.ltddgk.ui.user // Kiểm tra lại package của bạn

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ltddgk.ui.admin.AdminViewModel
import com.example.ltddgk.ui.admin.User // Đảm bảo dòng này không bị báo xám

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDashboard(viewModel: AdminViewModel) {
    // SỬA TẠI ĐÂY: userList trong ViewModel là SnapshotStateList nên truy cập trực tiếp
    val users = viewModel.userList

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "DANH SÁCH NGƯỜI DÙNG", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(users) { user ->
                UserItemRow(user)
            }
        }
    }
}

@Composable
fun UserItemRow(user: User) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Hiển thị ảnh đại diện
            AsyncImage(
                model = if (user.imageUrl.isEmpty()) "https://via.placeholder.com/150" else user.imageUrl,
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = user.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "@${user.username}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Vai trò: ${user.role}", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}