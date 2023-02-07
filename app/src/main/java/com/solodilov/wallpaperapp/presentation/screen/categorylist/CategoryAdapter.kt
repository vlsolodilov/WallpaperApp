package com.solodilov.wallpaperapp.presentation.screen.categorylist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.solodilov.wallpaperapp.databinding.ItemCategoryBinding
import com.solodilov.wallpaperapp.domain.entity.Category

class CategoryAdapter(
    private val onClick: (Category) -> Unit,
) : ListAdapter<Category, CategoryViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder =
        CategoryViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick
        )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
        Log.d("TAG", "onBindViewHolder: ")
    }

}

class CategoryViewHolder(
    private val binding: ItemCategoryBinding,
    private val onClick: (Category) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(category: Category) {

        binding.categoryName.text = category.name

        Glide
            .with(itemView)
            .load(category.image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.categoryImage)

        itemView.setOnClickListener { onClick(category) }
    }
}

private class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {

    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}