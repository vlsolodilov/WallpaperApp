package com.solodilov.wallpaperapp.domain.repository

import android.graphics.Bitmap
import androidx.paging.PagingData
import com.solodilov.wallpaperapp.domain.entity.Category
import com.solodilov.wallpaperapp.domain.entity.Image
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    fun getCategoryList(): Flow<List<Category>>
    fun getPreviewsByCategoryWithPaging(category: String): Flow<PagingData<Image>>
    suspend fun getImage(imageUrl: String): Bitmap
}