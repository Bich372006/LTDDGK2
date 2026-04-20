package com.example.ltddgk.ui.admin

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.ltddgk.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class AdminViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")

    var usernameInput = mutableStateOf("")
    var passwordInput = mutableStateOf("")
    var roleInput = mutableStateOf("")
    var imageUriInput = mutableStateOf<Uri?>(null)

    var editingUser = mutableStateOf<User?>(null)
    var userList = mutableStateListOf<User>()

    init { fetchUsers() }

    private fun fetchUsers() {
        usersCollection.addSnapshotListener { snapshot, _ ->
            snapshot?.let {
                userList.clear()
                for (doc in it.documents) {
                    val user = doc.toObject(User::class.java)
                    // 🔥 Gán document.id vào model để luôn có ID dùng cho Sửa/Xóa
                    user?.let { u -> userList.add(u.copy(id = doc.id)) }
                }
            }
        }
    }

    fun saveUser() {
        if (usernameInput.value.isNotBlank()) {
            val id = UUID.randomUUID().toString()
            val data = hashMapOf(
                "id" to id,
                "name" to usernameInput.value,
                "password" to passwordInput.value,
                "role" to roleInput.value.ifBlank { "user" },
                "avatarUrl" to (imageUriInput.value?.toString() ?: "")
            )
            usersCollection.document(id).set(data).addOnSuccessListener { clearForm() }
        }
    }

    fun updateUser() {
        editingUser.value?.let { current ->
            val data = hashMapOf<String, Any>(
                "name" to usernameInput.value,
                "password" to passwordInput.value,
                "role" to roleInput.value,
                "avatarUrl" to (imageUriInput.value?.toString() ?: "")
            )
            usersCollection.document(current.id).update(data).addOnSuccessListener { cancelEditing() }
        }
    }

    fun deleteUser(user: User) {
        usersCollection.document(user.id).delete()
    }

    fun startEditing(user: User) {
        editingUser.value = user
        usernameInput.value = user.name
        passwordInput.value = user.password
        roleInput.value = user.role
        imageUriInput.value = if (user.avatarUrl.isNotEmpty()) Uri.parse(user.avatarUrl) else null
    }

    fun cancelEditing() { editingUser.value = null; clearForm() }

    private fun clearForm() {
        usernameInput.value = ""; passwordInput.value = ""; roleInput.value = ""; imageUriInput.value = null
    }
}