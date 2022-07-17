package com.example.cleanarchitecturekotlin.features.photos

data class PhotoEntity(
    private val id: Int,
    private val author: String,
    private val width: Int,
    private val height: Int,
    private val download_url: String,
    private val like: Boolean,
    private val dislike: Boolean) {

    fun toPhoto() = Photo(id, download_url, like, dislike)
}