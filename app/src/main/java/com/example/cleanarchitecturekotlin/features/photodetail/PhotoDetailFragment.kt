package com.example.cleanarchitecturekotlin.features.photodetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.example.cleanarchitecturekotlin.R
import com.example.cleanarchitecturekotlin.core.exception.Failure
import com.example.cleanarchitecturekotlin.core.extension.*
import com.example.cleanarchitecturekotlin.core.platform.BaseFragment
import com.example.cleanarchitecturekotlin.databinding.FragmentPhotoDetailBinding
import com.example.cleanarchitecturekotlin.features.photos.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PhotoDetailFragment() : BaseFragment(){

    companion object {
        private const val PARAM_PHOTO = "param_photo"

        fun forPhoto(photo: PhotoView?) = PhotoDetailFragment().apply {
            arguments = bundleOf(PARAM_PHOTO to photo)
        }
    }

    @Inject
    lateinit var photoDetailAnimator: PhotoDetailAnimator

    private val photoDetailViewModel by viewModels<PhotoDetailViewModel>()

    private var photoDetailView = PhotoDetailView.empty

    private var _binding: FragmentPhotoDetailBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun layoutId() = R.layout.fragment_photo_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let { photoDetailAnimator.postponeEnterTransition(it) }

        with(photoDetailViewModel) {
            observe(photoDetail, ::renderPhotoDetail)
            failure(failure, ::handleFailure)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.photodetailProgress.visible()
        binding.photodetailContainer.invisible()

        val photoView = arguments?.get(PARAM_PHOTO) as PhotoView
        val id = photoView.id
        val like = photoView.like
        val dislike = photoView.dislike

        photoDetailView.like = like
        photoDetailView.dislike = dislike

        settingLikeDislike(like, dislike)

        photoDetailViewModel.loadPhotoDetail(id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onBackPressed() {
        photoDetailAnimator.fadeInvisible(binding.photodetailScrollview, binding.photodetail)
        val photoView = PhotoView(photoDetailView.id, photoDetailView.download_url, photoDetailView.like, photoDetailView.dislike)

        val intent = Intent(UPDATE_PHOTO_VIEW_INTENT).apply{
            putExtra("updatePhotoView", photoView)
        }
        requireActivity().sendBroadcast(intent)
    }

    // like, dislike init
    private fun settingLikeDislike(like: Boolean, dislike: Boolean){
        settingLike(like)
        settingDislike(dislike)
    }

    private fun settingLike(like: Boolean){
        val likeImage = if (like) R.drawable.selected_like else R.drawable.unselected_like

        binding.photodetailLikeImg.setImageResource(likeImage)
    }

    private fun settingDislike(dislike: Boolean){
        val dislikeImage = if (dislike) R.drawable.selected_dislike else R.drawable.unselected_dislike

        binding.photodetailDislikeImg.setImageResource(dislikeImage)
    }

    private fun clickListener(){
        Log.d("test", "clickListener")
        binding.photodetailLikeImg.setOnClickListener {
            photoDetailView.like = !photoDetailView.like

            settingLike(photoDetailView.like)
        }
        binding.photodetailDislikeImg.setOnClickListener {
            photoDetailView.dislike = !photoDetailView.dislike

            settingDislike(photoDetailView.dislike)
        }


    }

    private fun copyPhotoDetailView(photo: PhotoDetailView){
        val like = photoDetailView.like
        val dislike = photoDetailView.dislike
        photoDetailView = photo
        photoDetailView.like = like
        photoDetailView.dislike = dislike
    }

    private fun renderPhotoDetail(photo: PhotoDetailView?) {
        photo?.let {
            copyPhotoDetailView(photo)
            with(photo) {
                binding.photodetailPoster.loadFromUrl(download_url)
                binding.photodetailAuthor.text = author
                binding.photodetailWidth.text = width.toString()
                binding.photodetailHeight.text = height.toString()

                binding.photodetailProgress.invisible()
                binding.photodetailContainer.visible()

                clickListener()
            }
        }
        photoDetailAnimator.fadeVisible(binding.photodetailScrollview, binding.photodetail)
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> {
                notify(R.string.failure_network_connection)
            }
            is Failure.ServerError -> {
                notify(R.string.failure_server_error)
            }
            is PhotoFailure.NonExistentPhoto -> {
                notify(R.string.failure_movie_non_existent)
            }
            else -> {
                notify(R.string.failure_server_error)
            }
        }
    }
}
