package com.example.cleanarchitecturekotlin.features.photos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchitecturekotlin.R
import com.example.cleanarchitecturekotlin.core.exception.Failure
import com.example.cleanarchitecturekotlin.core.extension.failure
import com.example.cleanarchitecturekotlin.core.extension.invisible
import com.example.cleanarchitecturekotlin.core.extension.observe
import com.example.cleanarchitecturekotlin.core.extension.visible
import com.example.cleanarchitecturekotlin.core.functional.EndlessRecyclerViewScrollListener
import com.example.cleanarchitecturekotlin.core.navigation.Navigator
import com.example.cleanarchitecturekotlin.core.platform.BaseFragment
import com.example.cleanarchitecturekotlin.databinding.FragmentPhotosBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PhotosFragment : BaseFragment(){

    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var photosAdapter: PhotosAdapter
    @Inject
    lateinit var photosBroadcastReceiver: PhotosBroadcastReceiver
    @Inject
    lateinit var photosIntentFilter: PhotosIntentFilter

    private val photosViewModel: PhotosViewModel by viewModels()

    private var _binding: FragmentPhotosBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun layoutId() = R.layout.fragment_photos

    // recevier
    private val photosReceiver by lazy{
        photosBroadcastReceiver.receiver(this)
    }

    var isloading = false
    var linearLayoutManager = LinearLayoutManager(context)
    private var scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
        override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
            if(!isloading){
                photosViewModel.loadNextDataFromApi(page)
                isloading = true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(photosViewModel) {
            observe(photos, ::renderPhotosList)
            failure(failure, ::handleFailure)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotosBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        loadPhotosList()
    }

    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(photosReceiver, photosIntentFilter.intentFilter())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        requireActivity().unregisterReceiver(photosReceiver)
    }

    private fun initializeView() {
        scrollListener.resetState()

        binding.photoList.layoutManager = linearLayoutManager
        binding.photoList.addOnScrollListener(scrollListener)

        binding.photoList.adapter = photosAdapter
        photosAdapter.clickListener = { photo ->
            navigator.showPhotoDetail(requireActivity(), photo)
        }
    }

    private fun loadPhotosList() {
        binding.errorView.invisible()
        binding.photoList.visible()
        photosViewModel.loadPhotos(1, 10)
    }

    private fun renderPhotosList(photos: List<PhotoView>?) {
        photosAdapter.collection.addAll(photos.orEmpty().toMutableList())
        photosAdapter.notifyDataSetChanged()

        isloading = false
    }

    fun updatePhoto(photo: PhotoView?) {
        photo?.let{
            photosAdapter.updatePhotoView(it)
            photosAdapter.notifyDataSetChanged()
        }
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is Failure.ServerError -> renderFailure(R.string.failure_server_error)
            is PhotoFailure.ListNotAvailable -> renderFailure(R.string.failure_movies_list_unavailable)
            else -> renderFailure(R.string.failure_server_error)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        binding.photoList.invisible()
        binding.errorView.visible()
    }

}

