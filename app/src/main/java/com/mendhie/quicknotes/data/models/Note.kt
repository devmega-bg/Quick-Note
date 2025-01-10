package com.mendhie.quicknotes.data.models

data class Note(
    val id: String = "",
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val userId: String = "",
    val category: String = ""
)
