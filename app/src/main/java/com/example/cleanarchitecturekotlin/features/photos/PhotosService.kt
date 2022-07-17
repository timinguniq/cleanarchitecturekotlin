package com.example.cleanarchitecturekotlin.features.photos

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosService
@Inject constructor(retrofit: Retrofit): PhotosApi {
    private val photosApi by lazy { retrofit.create(PhotosApi::class.java) }

    override fun photos(page: Int, limit: Int) = photosApi.photos(page, limit)
    override fun photoDetail(photoId: Int) = photosApi.photoDetail(photoId)

}