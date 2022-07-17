package com.example.cleanarchitecturekotlin.features.photos

import android.os.Parcelable
import com.example.cleanarchitecturekotlin.core.interactor.UseCase
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

class GetPhotos
@Inject constructor(private val photosRepository: PhotosRepository) : UseCase<List<Photo>, GetPhotos.Params>(){

    override suspend fun run(params: Params) = photosRepository.photos(params.page, params.limit)

    data class Params(val page: Int, val limit: Int)

}
