package com.example.cleanarchitecturekotlin.features.photodetail

import com.example.cleanarchitecturekotlin.core.interactor.UseCase
import com.example.cleanarchitecturekotlin.features.photos.PhotosRepository
import javax.inject.Inject

class GetPhotoDetail
@Inject constructor(private val photosRepository: PhotosRepository):
    UseCase<PhotoDetail, GetPhotoDetail.Params>() {

    override suspend fun run(params: Params) = photosRepository.photoDetail(params.id)

    data class Params(val id: Int)

}