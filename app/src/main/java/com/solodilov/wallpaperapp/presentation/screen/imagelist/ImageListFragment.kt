package com.solodilov.wallpaperapp.presentation.screen.imagelist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.solodilov.wallpaperapp.App
import com.solodilov.wallpaperapp.R
import com.solodilov.wallpaperapp.databinding.FragmentImageListBinding
import com.solodilov.wallpaperapp.domain.entity.Image
import com.solodilov.wallpaperapp.presentation.common.ViewModelFactory
import com.solodilov.wallpaperapp.presentation.common.viewBinding

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class ImageListFragment : Fragment(R.layout.fragment_image_list) {

    private val binding by viewBinding(FragmentImageListBinding::bind)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ImageListViewModel by viewModels { viewModelFactory }

    private var imageAdapter: ImageAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.data.collectLatest {
                    imageAdapter?.submitData(it)
                    Log.d("TAG", "onViewCreated: $it")
                }
            }
        }
    }

    private fun initViews() {
        arguments?.let {
            val args = ImageListFragmentArgs.fromBundle(it)
            val category = args.category
            binding.imageListToolbar.title = category.replaceFirstChar{ it.uppercase() }
            viewModel.setCategory(category)
        }

        imageAdapter = ImageAdapter(::startImageFragment)
        binding.imageList.adapter = imageAdapter?.withLoadStateHeaderAndFooter(
            header = ImageLoadStateAdapter { imageAdapter?.retry() },
            footer = ImageLoadStateAdapter { imageAdapter?.retry() }
        )

        imageAdapter?.addLoadStateListener { state ->
            Log.d("TAG", "initViews: ${state.refresh}")
            with(binding) {
                imageList.isVisible = state.refresh != LoadState.Loading
                progressBar.isVisible = state.refresh == LoadState.Loading
                errorLayout.root.isVisible = state.refresh is LoadState.Error
                        && imageAdapter?.itemCount == 0
            }
        }

        binding.errorLayout.tryButton.setOnClickListener { imageAdapter?.retry() }
    }

    private fun startImageFragment(image: Image) {
        val action = ImageListFragmentDirections
            .actionImageListFragmentToImageFragment(image.url)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        imageAdapter = null
        super.onDestroyView()
    }
}