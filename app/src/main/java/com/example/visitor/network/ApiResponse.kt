package com.example.visitor.network
data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T?
)