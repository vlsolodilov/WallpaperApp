package com.solodilov.wallpaperapp.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.paging.PagingData
import com.solodilov.wallpaperapp.data.datasource.ImageRemoteDatasource
import com.solodilov.wallpaperapp.data.mapper.ImageMapper
import com.solodilov.wallpaperapp.domain.entity.Category
import com.solodilov.wallpaperapp.domain.entity.Image
import com.solodilov.wallpaperapp.domain.repository.ImageRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val remoteDataSource: ImageRemoteDatasource,
) : ImageRepository {

    override fun getCategoryList(): Flow<List<Category>> = flow {
        emit(coroutineScope {
            remoteDataSource.getCategoryNameList()
                .map { async { remoteDataSource.getPreviewByCategory(it) } }
                .awaitAll()
        })
    }

    override fun getPreviewsByCategoryWithPaging(category: String): Flow<PagingData<Image>> =
        remoteDataSource.getPreviewsByCategoryWithPaging(category)

    override suspend fun getImage(imageUrl: String): Bitmap {
        val inputStream = remoteDataSource.getImage(imageUrl).byteStream()
        return BitmapFactory.decodeStream(inputStream)
    }

}