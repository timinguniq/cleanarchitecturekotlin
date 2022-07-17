package com.example.cleanarchitecturekotlin.features.photodetail

import android.os.Parcel
import com.example.cleanarchitecturekotlin.core.extension.empty
import com.example.cleanarchitecturekotlin.core.platform.KParcelable
import com.example.cleanarchitecturekotlin.core.platform.parcelableCreator

data class PhotoDetailView(
    val id: Int,
    val author: String,
    val width: Int,
    val height: Int,
    val download_url: String,
    var like: Boolean,
    var dislike: Boolean
): KParcelable {
    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::PhotoDetailView)

        val empty = PhotoDetailView(0, String.empty(), 0, 0, String.empty(), like = false, dislike = false)
    }

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString()!!, parcel.readInt(),
        parcel.readInt(), parcel.readString()!!, parcel.readBoolean(), parcel.readBoolean())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeInt(id)
            writeString(author)
            writeInt(width)
            writeInt(height)
            writeString(download_url)
            writeBoolean(like)
            writeBoolean(dislike)
        }
    }
}
