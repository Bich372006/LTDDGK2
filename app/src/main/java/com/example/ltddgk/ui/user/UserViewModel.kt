package com.example.ltddgk.ui.user

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ltddgk.data.model.User
import com.example.ltddgk.data.repository.UserRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val repo = UserRepository()

    var monHocList = mutableStateOf(listOf<User>())
    var isLoading = mutableStateOf(true)

    init {
        loadMonHoc()
    }

    private fun loadMonHoc() {
        viewModelScope.launch {
            repo.getUsers().collectLatest { list ->
                monHocList.value = list
                isLoading.value = false
            }
        }
    }
}