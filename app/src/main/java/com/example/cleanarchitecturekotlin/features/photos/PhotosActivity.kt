package com.example.cleanarchitecturekotlin.features.photos

import android.content.Context
import android.content.Intent
import com.example.cleanarchitecturekotlin.core.platform.BaseActivity

class PhotosActivity : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, PhotosActivity::class.java)
    }

    override fun fragment() = PhotosFragment()
}