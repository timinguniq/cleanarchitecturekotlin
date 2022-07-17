package com.example.cleanarchitecturekotlin.features.photos

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import javax.inject.Inject

class PhotosBroadcastReceiver
@Inject constructor(){
    fun receiver(fragment: PhotosFragment): BroadcastReceiver {
        return object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val photoView = intent.getParcelableExtra<PhotoView>("updatePhotoView")
                if (intent.action == UPDATE_PHOTO_VIEW_INTENT) {
                    fragment.updatePhoto(photoView)
                }
            }
        }

    }
}