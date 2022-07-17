package com.example.cleanarchitecturekotlin.features.photodetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecturekotlin.core.platform.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel
@Inject constructor(private val getPhotoDetail: GetPhotoDetail) : BaseViewModel() {

    private val _photoDetail: MutableLiveData<PhotoDetailView> = MutableLiveData()
    val photoDetail: LiveData<PhotoDetailView> = _photoDetail

    fun loadPhotoDetail(photoId: Int) =
        getPhotoDetail(GetPhotoDetail.Params(photoId), viewModelScope) {
            it.fold(
                ::handleFailure,
                ::handlePhotoDetail
            )
        }

    private fun handlePhotoDetail(photo: PhotoDetail) {
        _photoDetail.value = PhotoDetailView(
            photo.id, photo.author, photo.width, photo.height,
            photo.download_url, photo.like, photo.dislike
        )
    }
}



