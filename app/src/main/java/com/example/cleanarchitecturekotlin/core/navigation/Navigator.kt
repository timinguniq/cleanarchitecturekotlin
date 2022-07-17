package com.example.cleanarchitecturekotlin.core.navigation

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.example.cleanarchitecturekotlin.features.login.Authenticator
import com.example.cleanarchitecturekotlin.features.login.LoginActivity
import com.example.cleanarchitecturekotlin.features.photodetail.PhotoDetailActivity
import com.example.cleanarchitecturekotlin.features.photos.PhotoView
import com.example.cleanarchitecturekotlin.features.photos.PhotosActivity
import com.example.cleanarchitecturekotlin.features.photos.PhotosFragment
import com.example.cleanarchitecturekotlin.features.photos.PhotosViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator
@Inject constructor(private val authenticator: Authenticator) {

    private fun showLogin(context: Context) =
        context.startActivity(LoginActivity.callingIntent(context))

    fun showMain(context: Context) {
        when (authenticator.userLoggedIn()) {
            true -> showPhotos(context)
            false -> showLogin(context)
        }
    }

    private fun showPhotos(context: Context) =
        context.startActivity(PhotosActivity.callingIntent(context))

    fun showPhotoDetail(activity: FragmentActivity, photo: PhotoView){
        val intent = PhotoDetailActivity.callingIntent(activity, photo)
        activity.startActivity(intent)
    }
}