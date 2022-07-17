package com.example.cleanarchitecturekotlin.features.photos

import com.example.cleanarchitecturekotlin.core.extension.empty

data class Photo(val id: Int, val download_url: String, val like: Boolean, val dislike: Boolean) {
    companion object{
        val empty = Photo(0, String.empty(), like = false, dislike = false)
    }
}