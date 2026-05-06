package com.example.visitor.ui.utilis

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
fun Double.toAriary(): String = "${String.format("%.0f", this)} Ar"

@SuppressLint("DefaultLocale")
fun Double.toAriaryShort(): String = String.format("%.0f", this)

fun Int.daysLabel(): String = "${this}j"
