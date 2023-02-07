package com.solodilov.wallpaperapp.presentation.screen.image

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.solodilov.wallpaperapp.App
import com.solodilov.wallpaperapp.R
import com.solodilov.wallpaperapp.databinding.FragmentImageBinding
import com.solodilov.wallpaperapp.presentation.common.*
import javax.inject.Inject

class ImageFragment : Fragment(R.layout.fragment_image) {

    private val binding by viewBinding(FragmentImageBinding::bind)

    private var wallpaper: Bitmap? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ImageViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        collectFlow(viewModel.uiState, ::handleState)
    }

    private fun initViews() {
        arguments?.let {
            val args = ImageFragmentArgs.fromBundle(it)
            val imageUrl = args.imageUrl
            viewModel.setImageUrl(imageUrl)

        }

        binding.setWallpaper.setOnClickListener { setWallpaper() }
        binding.errorLayout.tryButton.setOnClickListener { viewModel.getData() }
    }

    private fun handleState(state: UiState<Bitmap>) = with(binding) {
        progressBar.isVisible = state is UiState.Loading
        errorLayout.root.isVisible = state is UiState.Error
        setWallpaper.isVisible = state is UiState.Success

        Log.d("TAG", "handleState: $state")

        state
            .onSuccess { data ->
                binding.largeImage.setImageBitmap(data)
                wallpaper = data
            }
            .onError { error -> showToast(error.message.toString()) }
    }

    private fun setWallpaper() {
        val manager = WallpaperManager.getInstance(requireActivity().applicationContext);
        try {
            if (wallpaper != null) {
                manager.setBitmap(wallpaper);
                showToast(getString(R.string.success))
            }
        } catch (e: Exception) {
            showToast(getString(R.string.error))
        }
    }
}