package com.example.cleanarchitecturekotlin.features.photodetail

import com.example.cleanarchitecturekotlin.core.extension.empty

data class PhotoDetailEntity(
    private val id: Int,
    private val author: String,
    private val width: Int,
    private val height: Int,
    private val download_url: String,
    private val like: Boolean,
    private val dislike: Boolean) {

    companion object {
        val empty = PhotoDetailEntity(
            0, String.empty(), 0, 0,
            String.empty(), like = false, dislike = false
        )
    }

    fun toPhotoDetail() = PhotoDetail(id, author, width, height, download_url, like, dislike)
}