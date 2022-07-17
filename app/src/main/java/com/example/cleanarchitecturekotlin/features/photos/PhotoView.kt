package com.example.cleanarchitecturekotlin.features.photos

import android.os.Parcel
import com.example.cleanarchitecturekotlin.core.platform.KParcelable
import com.example.cleanarchitecturekotlin.core.platform.parcelableCreator

data class PhotoView(val id: Int, val download_url: String, var like: Boolean, var dislike: Boolean) : KParcelable {
    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::PhotoView)
    }

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString()!!, parcel.readBoolean(), parcel.readBoolean())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeInt(id)
            writeString(download_url)
            writeBoolean(like)
            writeBoolean(dislike)
        }
    }
}