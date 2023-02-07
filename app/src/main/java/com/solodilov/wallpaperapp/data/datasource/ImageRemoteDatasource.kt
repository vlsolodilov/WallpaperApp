package com.solodilov.wallpaperapp.data.datasource

import androidx.paging.PagingData
import com.solodilov.wallpaperapp.domain.entity.Category
import com.solodilov.wallpaperapp.domain.entity.Image
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface ImageRemoteDatasource {

    fun getCategoryNameList(): List<String>

    suspend fun getPreviewByCategory(category: String): Category

    fun getPreviewsByCategoryWithPaging(category: String): Flow<PagingData<Image>>

    suspend fun getImage(imageUrl: String): ResponseBody

}