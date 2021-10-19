package com.example.network.response

data class CommonPic(
    val url: String,
    val width: Int,
    val height: Int,
    var tags: String?,
    var imageURL: String?,
    var fullHDURL: String?,
    var id: Int?,
    var videoId: String?
)
