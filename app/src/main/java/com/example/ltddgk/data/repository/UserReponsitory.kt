package com.example.ltddgk.data.repository

import com.example.ltddgk.data.model.User
import com.example.ltddgk.until.FirebaseUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

class UserRepository {
    private val collection = FirebaseUtils.db.collection(FirebaseUtils.COLLECTION_USER)

    // Lấy danh sách người dùng thời gian thực
    fun getUsers() = callbackFlow<List<User>> {
        val listener = collection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                // Nếu có lỗi, đóng flow
                close(error)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val list = snapshot.documents.mapNotNull { document ->
                    // Chuyển từ Document sang Object User
                    // it.id là String, khớp với User(id: String) đã sửa ở trên
                    document.toObject(User::class.java)?.copy(id = document.id)
                }
                trySend(list).isSuccess
            }
        }

        // Quan trọng: Gỡ bỏ listener khi không còn sử dụng flow để tiết kiệm tài nguyên
        awaitClose { listener.remove() }
    }.flowOn(Dispatchers.IO)

    // Hàm xóa người dùng
    fun deleteUser(id: String, onComplete: (Boolean) -> Unit = {}) {
        collection.document(id).delete()
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }
}