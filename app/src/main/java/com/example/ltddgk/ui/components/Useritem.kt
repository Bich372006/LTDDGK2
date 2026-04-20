package com.example.ltddgk.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

import com.example.ltddgk.data.model.User

@Composable
fun UserItem(user: User, isAdmin: Boolean, onDelete: () -> Unit) {
    Card(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = user.avatarUrl.ifBlank { "https://via.placeholder.com/150" }, // Dùng user.avatarUrl
                contentDescription = null,
                modifier = Modifier.size(50.dp).clip(CircleShape)
            )
            Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                Text(text = user.name, fontWeight = FontWeight.Bold) // Dùng user.name
                Text(text = "Quyền: ${user.role}", style = MaterialTheme.typography.bodySmall) // Dùng user.role
            }
            if (isAdmin) {
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                }
            }
        }
    }
}