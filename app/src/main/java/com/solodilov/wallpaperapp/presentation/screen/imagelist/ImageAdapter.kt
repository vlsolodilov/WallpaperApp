package com.solodilov.wallpaperapp.presentation.screen.imagelist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.solodilov.wallpaperapp.databinding.ItemPreviewBinding
import com.solodilov.wallpaperapp.domain.entity.Image

class ImageAdapter(
    private val onClick: (Image) -> Unit,
) : PagingDataAdapter<Image, ImageViewHolder>(ImageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(
            ItemPreviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick
        )

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
        Log.d("TAG", "onBindViewHolder: image")
    }

}

class ImageViewHolder(
    private val binding: ItemPreviewBinding,
    private val onClick: (Image) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(image: Image?) {

        Glide
            .with(itemView)
            .load(image?.previewUrl)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.preview)

        itemView.setOnClickListener { image?.let(onClick) }
    }
}

private class ImageDiffCallback : DiffUtil.ItemCallback<Image>() {

    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }
}