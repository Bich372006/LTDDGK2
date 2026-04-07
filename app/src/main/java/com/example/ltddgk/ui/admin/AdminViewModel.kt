package com.example.ltddgk.ui.admin

import android.net.Uri // 🔥 CỰC KỲ QUAN TRỌNG: Phải có dòng này
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

// Model User khớp với các thuộc tính bạn dùng trong UI
data class User(
    val id: Int,
    val name: String,
    val pass: String,
    val username: String = "",
    val role: String = "Sinh viên",
    val imageUrl: String = ""
)

class AdminViewModel : ViewModel() {
    // 1. State cho Form nhập liệu
    var nameInput = mutableStateOf("")
    var usernameInput = mutableStateOf("")
    var passwordInput = mutableStateOf("")

    // 🔹 BIẾN NÀY GIÚP HẾT LỖI ĐỎ Ở ADMIN DASHBOARD
    var imageUriInput = mutableStateOf<Uri?>(null)

    // 2. Lưu user đang được sửa (null = đang ở chế độ Thêm mới)
    var editingUser = mutableStateOf<User?>(null)

    // 3. Danh sách User mẫu
    var userList = mutableStateListOf(
        User(1, "Bích", "123", "bich_vku", "Admin"),
        User(2, "Nguyễn Văn A", "abc", "vana123", "Sinh viên")
    )

    // Hàm Thêm mới User
    fun saveUser() {
        if (nameInput.value.isNotBlank() && passwordInput.value.isNotBlank()) {
            val newId = if (userList.isEmpty()) 1 else userList.maxOf { it.id } + 1

            // Chuyển Uri ảnh sang String để lưu vào Model
            val imagePath = imageUriInput.value?.toString() ?: ""

            val newUser = User(
                id = newId,
                name = nameInput.value,
                pass = passwordInput.value,
                username = usernameInput.value.ifBlank { "user_$newId" },
                role = "Sinh viên",
                imageUrl = imagePath // 🔥 Lưu đường dẫn ảnh vào đây
            )
            userList.add(newUser)
            clearForm()
        }
    }

    // Hàm chuẩn bị dữ liệu để Sửa
    fun startEditing(user: User) {
        editingUser.value = user
        nameInput.value = user.name
        usernameInput.value = user.username
        passwordInput.value = user.pass

        // Hiển thị lại ảnh cũ lên form nếu có
        imageUriInput.value = if (user.imageUrl.isNotEmpty()) Uri.parse(user.imageUrl) else null
    }

    // Hàm cập nhật User sau khi sửa
    fun updateUser() {
        val current = editingUser.value
        if (current != null) {
            val index = userList.indexOfFirst { it.id == current.id }
            if (index != -1) {
                val imagePath = imageUriInput.value?.toString() ?: ""

                userList[index] = current.copy(
                    name = nameInput.value,
                    pass = passwordInput.value,
                    username = usernameInput.value,
                    imageUrl = imagePath // 🔥 Cập nhật ảnh mới
                )
            }
            cancelEditing()
        }
    }

    // Hàm Xóa User
    fun deleteUser(user: User) {
        userList.remove(user)
    }

    // Hàm Hủy chế độ sửa
    fun cancelEditing() {
        editingUser.value = null
        clearForm()
    }

    // Hàm xóa trắng các ô nhập liệu
    private fun clearForm() {
        nameInput.value = ""
        usernameInput.value = ""
        passwordInput.value = ""
        imageUriInput.value = null // 🔥 Xóa ảnh trên form
    }
}