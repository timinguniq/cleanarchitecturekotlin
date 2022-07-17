package com.example.cleanarchitecturekotlin.features.photos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecturekotlin.core.platform.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel
@Inject constructor(private val getPhotos: GetPhotos) : BaseViewModel(){
    private val _photos: MutableLiveData<List<PhotoView>> = MutableLiveData()
    val photos: LiveData<List<PhotoView>> = _photos

    fun loadPhotos(page: Int, limit: Int) =
        getPhotos(GetPhotos.Params(page, limit), viewModelScope) { it.fold(::handleFailure, ::handlePhotoList) }

    private fun handlePhotoList(photos: List<Photo>) {
        _photos.value = photos.map { PhotoView(it.id, it.download_url, it.like, it.dislike) }
    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    fun loadNextDataFromApi(offset: Int) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
        loadPhotos(offset+1, 10)
    }
}
