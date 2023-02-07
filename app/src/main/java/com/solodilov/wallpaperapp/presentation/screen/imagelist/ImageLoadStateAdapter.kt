package com.solodilov.wallpaperapp.presentation.screen.imagelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.solodilov.wallpaperapp.databinding.ItemImagesFooterBinding

class ImageLoadStateAdapter(
    private val retry: () -> Unit,
) : LoadStateAdapter<ImageLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: ImageLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): ImageLoadStateViewHolder {
        return ImageLoadStateViewHolder(LayoutInflater.from(parent.context), parent, retry)
    }
}

class ImageLoadStateViewHolder(
    private val binding: ItemImagesFooterBinding,
    private val retry: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {

        binding.appendProgress.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error

    }

    companion object {
        operator fun invoke(
            layoutInflater: LayoutInflater,
            parent: ViewGroup? = null,
            retry: () -> Unit,
        ): ImageLoadStateViewHolder {
            return ImageLoadStateViewHolder(
                ItemImagesFooterBinding.inflate(
                    layoutInflater,
                    parent,
                    false,
                ),
                retry
            )
        }
    }
}
