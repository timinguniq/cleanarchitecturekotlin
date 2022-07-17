package com.example.cleanarchitecturekotlin.features.photos

import com.example.cleanarchitecturekotlin.features.photodetail.PhotoDetailEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface PhotosApi {
    companion object {
        private const val PARAM_PHOTO_PAGE = "page"
        private const val PARAM_PHOTO_LIMIT = "limit"
        private const val PARAM_PHOTO_DETAIL = "id"

        private const val PHOTOS = "v2/list"
        private const val PHOTODETAIL = "id/{${PARAM_PHOTO_DETAIL}}/info"
    }

    @GET(PHOTOS)
    fun photos(@Query(PARAM_PHOTO_PAGE) page: Int, @Query(PARAM_PHOTO_LIMIT) limit: Int): Call<List<PhotoEntity>>

    @GET(PHOTODETAIL)
    fun photoDetail(@Path(PARAM_PHOTO_DETAIL) photoId: Int) : Call<PhotoDetailEntity>
}