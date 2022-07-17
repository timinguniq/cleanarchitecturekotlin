package com.example.cleanarchitecturekotlin.features.photos

import android.content.IntentFilter
import javax.inject.Inject

class PhotosIntentFilter
@Inject constructor(){
    fun intentFilter(): IntentFilter {
        val intentFilter = IntentFilter()
        intentFilter.addAction(UPDATE_PHOTO_VIEW_INTENT)
        return intentFilter
    }
}
