package com.example.practicsing.data.model

data class Diary(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val date: Long = System.currentTimeMillis()
)