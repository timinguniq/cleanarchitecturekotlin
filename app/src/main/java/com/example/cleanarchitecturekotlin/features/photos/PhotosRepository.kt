package com.example.cleanarchitecturekotlin.features.photos

import com.example.cleanarchitecturekotlin.core.exception.Failure
import com.example.cleanarchitecturekotlin.core.functional.Either
import com.example.cleanarchitecturekotlin.core.platform.NetworkHandler
import com.example.cleanarchitecturekotlin.features.photodetail.PhotoDetail
import com.example.cleanarchitecturekotlin.features.photodetail.PhotoDetailEntity
import retrofit2.Call
import javax.inject.Inject

interface PhotosRepository {
    fun photos(page: Int, limit: Int): Either<Failure, List<Photo>>
    fun photoDetail(photoId: Int): Either<Failure, PhotoDetail>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: PhotosService
    ) : PhotosRepository {

        override fun photos(page: Int, limit: Int): Either<Failure, List<Photo>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.photos(page, limit),
                    { it.map { photoEntity -> photoEntity.toPhoto() } },
                    emptyList()
                )
                false -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun photoDetail(photoId: Int): Either<Failure, PhotoDetail> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.photoDetail(photoId),
                    { it.toPhotoDetail() },
                    PhotoDetailEntity.empty
                )
                false -> Either.Left(Failure.NetworkConnection)
            }
        }

        private fun <T, R> request(
            call: Call<T>,
            transform: (T) -> R,
            default: T
        ): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Either.Right(transform((response.body() ?: default)))
                    false -> Either.Left(Failure.ServerError)
                }
            } catch (exception: Throwable) {
                Either.Left(Failure.ServerError)
            }
        }
    }

}