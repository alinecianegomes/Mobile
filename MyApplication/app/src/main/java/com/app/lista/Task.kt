package com.app.lista

data class Task(
    val id: Long = System.currentTimeMillis(),
    val title: String,
    val isCompleted: Boolean = false
)