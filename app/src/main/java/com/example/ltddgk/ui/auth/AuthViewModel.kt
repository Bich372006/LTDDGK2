package com.example.ltddgk.ui.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    private val _loginSuccess = mutableStateOf(false)
    val loginSuccess: State<Boolean> = _loginSuccess

    private val _isRegisterSuccess = mutableStateOf(false)
    val isSuccess: State<Boolean> = _isRegisterSuccess

    // Đăng nhập: Thêm trim() để tránh lỗi thừa dấu cách
    fun login(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            _errorMessage.value = "Vui lòng nhập đầy đủ thông tin!"
            return
        }
        _isLoading.value = true
        _errorMessage.value = ""

        // Khớp với tài khoản của Bích trong ảnh
        if (email.trim() == "tranbich@gmail.com" && pass == "Bich3706@") {
            _loginSuccess.value = true
        } else {
            _errorMessage.value = "Email hoặc mật khẩu không chính xác!"
        }
        _isLoading.value = false
    }

    // Đăng ký: Truyền đủ 3 tham số để hết lỗi 'confirmPass'
    fun register(email: String, pass: String, confirmPass: String) {
        if (email.isBlank() || pass.isBlank() || confirmPass.isBlank()) {
            _errorMessage.value = "Không được để trống thông tin!"
            return
        }
        if (pass != confirmPass) {
            _errorMessage.value = "Mật khẩu xác nhận không khớp!"
            return
        }
        _isLoading.value = true
        _isRegisterSuccess.value = true
        _isLoading.value = false
    }

    fun forgotPassword(email: String) {
        if (email.isBlank()) {
            _errorMessage.value = "Nhập email để lấy lại mật khẩu!"
        } else {
            _errorMessage.value = "Link đặt lại mật khẩu đã được gửi!"
        }
    }

    fun resetLoginStatus() { _loginSuccess.value = false }
    fun resetState() {
        _isRegisterSuccess.value = false
        _errorMessage.value = ""
    }
}