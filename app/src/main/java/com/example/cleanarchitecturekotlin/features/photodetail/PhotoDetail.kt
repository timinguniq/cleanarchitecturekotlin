package com.example.cleanarchitecturekotlin.features.photodetail

import com.example.cleanarchitecturekotlin.core.extension.empty

data class PhotoDetail(
    val id: Int,
    val author: String,
    val width: Int,
    val height: Int,
    val download_url: String,
    val like: Boolean,
    val dislike: Boolean
){
    companion object {
        val empty = PhotoDetail(
            0, String.empty(), 0, 0,
            String.empty(), like = false, dislike = false,
        )
    }
}