package com.example.cleanarchitecturekotlin.features.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchitecturekotlin.R
import com.example.cleanarchitecturekotlin.core.extension.loadFromUrl
import com.example.cleanarchitecturekotlin.databinding.ItemPhotoBinding
import javax.inject.Inject
import kotlin.properties.Delegates

class PhotosAdapter
@Inject constructor() : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {
    internal var collection: MutableList<PhotoView> by Delegates.observable(mutableListOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var clickListener: (PhotoView) -> Unit = { _ -> }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position], clickListener)

    override fun getItemCount() = collection.size

    fun updatePhotoView(photoView: PhotoView){
        collection = collection.map { if(it.id == photoView.id) photoView else it }.toMutableList()
    }

    class ViewHolder(private val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photoView: PhotoView, clickListener: (PhotoView) -> Unit) {
            binding.photoPoster.loadFromUrl(photoView.download_url)
            var likeImage = if (photoView.like) R.drawable.selected_like else R.drawable.unselected_like
            binding.photoLikeImg.setImageResource(likeImage)
            var dislikeImage = if(photoView.dislike) R.drawable.selected_dislike else R.drawable.unselected_dislike
            binding.photoDislikeImg.setImageResource(dislikeImage)

            binding.root.setOnClickListener {
                clickListener(
                    photoView
                )
            }

            binding.photoLikeImg.setOnClickListener {
                photoView.like = !photoView.like
                likeImage= if (photoView.like) R.drawable.selected_like else R.drawable.unselected_like
                binding.photoLikeImg.setImageResource(likeImage)
            }

            binding.photoDislikeImg.setOnClickListener {
                photoView.dislike = !photoView.dislike
                dislikeImage = if(photoView.dislike) R.drawable.selected_dislike else R.drawable.unselected_dislike
                binding.photoDislikeImg.setImageResource(dislikeImage)
            }
        }
    }
}
