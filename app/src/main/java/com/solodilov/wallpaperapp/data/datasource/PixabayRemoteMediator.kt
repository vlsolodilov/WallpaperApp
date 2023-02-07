package com.solodilov.wallpaperapp.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.solodilov.wallpaperapp.data.datasource.database.ImageDao
import com.solodilov.wallpaperapp.data.datasource.database.entity.ImageDb
import com.solodilov.wallpaperapp.data.datasource.network.PixabayApi
import com.solodilov.wallpaperapp.data.datasource.network.PixabayApi.Companion.STARTING_PAGE_INDEX
import com.solodilov.wallpaperapp.data.mapper.ImageMapper


@ExperimentalPagingApi
class ImageRemoteMediator(
    private val category: String,
    private val service: PixabayApi,
    private val imageDao: ImageDao,
    private val mapper: ImageMapper,
) : RemoteMediator<Int, ImageDb>() {

    private var pageIndex = STARTING_PAGE_INDEX

    override suspend fun load(loadType: LoadType, state: PagingState<Int, ImageDb>): MediatorResult {

        pageIndex = getPageIndex(loadType)
            ?: return MediatorResult.Success(endOfPaginationReached = true)

        return try {
            val apiResponse = service.getImagesByCategoryWithPaging(category, pageIndex, state.config.pageSize)
            val images = mapper.mapImageListDtoToImageListDb(category, apiResponse)
            if (loadType == LoadType.REFRESH) {
                imageDao.refreshImages(images)
            } else {
                imageDao.insertAll(images)
            }
            MediatorResult.Success(
                endOfPaginationReached = images.size < state.config.pageSize
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private fun getPageIndex(loadType: LoadType): Int? =
        when (loadType) {
            LoadType.REFRESH -> STARTING_PAGE_INDEX
            LoadType.PREPEND -> null
            LoadType.APPEND -> ++pageIndex
        }

}
