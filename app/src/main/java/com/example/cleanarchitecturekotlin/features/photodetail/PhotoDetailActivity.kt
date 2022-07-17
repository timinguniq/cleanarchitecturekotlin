package com.example.cleanarchitecturekotlin.features.photodetail

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.example.cleanarchitecturekotlin.core.platform.BaseActivity
import com.example.cleanarchitecturekotlin.features.photos.PhotoView
import com.example.cleanarchitecturekotlin.features.photos.PhotosViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoDetailActivity : BaseActivity() {

    companion object {
        private const val INTENT_EXTRA_PARAM_PHOTO =
            "com.cleanarchitecturekotlin.INTENT_PARAM_PHOTO"

        fun callingIntent(context: Context, photo: PhotoView) =
            Intent(context, PhotoDetailActivity::class.java).apply {
                putExtra(PhotoDetailActivity.INTENT_EXTRA_PARAM_PHOTO, photo)
            }
    }

    override fun fragment() =
        PhotoDetailFragment.forPhoto(intent.getParcelableExtra(INTENT_EXTRA_PARAM_PHOTO))
}
