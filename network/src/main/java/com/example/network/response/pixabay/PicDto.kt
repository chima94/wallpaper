package com.example.network.response.pixabay

data class PicDto(
    val hits: List<HitX>,
    val total: Int,
    val totalHits: Int
)