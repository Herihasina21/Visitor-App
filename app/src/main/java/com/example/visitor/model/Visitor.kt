package com.example.visitor.model

data class Visitor(
    val visitorId: Long? = null,
    val name: String = "",
    val numberOfDays: Int = 0,
    val dailyRate: Double = 0.0,
    val totalFee: Double? = null
)