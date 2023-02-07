package com.solodilov.wallpaperapp.data.datasource

import androidx.paging.*
import com.solodilov.wallpaperapp.data.datasource.database.ImageDao
import com.solodilov.wallpaperapp.data.datasource.network.PixabayApi
import com.solodilov.wallpaperapp.data.mapper.ImageMapper
import com.solodilov.wallpaperapp.domain.entity.Category
import com.solodilov.wallpaperapp.domain.entity.Image
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.ResponseBody
import javax.inject.Inject

class ImageRemoteDatasourceImpl @Inject constructor(
    private val api: PixabayApi,
    private val dao: ImageDao,
    private val mapper: ImageMapper,
) : ImageRemoteDatasource {

    override fun getCategoryNameList(): List<String> =
        listOf(
            "backgrounds",
            "fashion",
            "nature",
            "science",
            "education",
            "feelings",
            "health",
            "people",
        )

    override suspend fun getPreviewByCategory(category: String): Category =
        mapper.mapImageListDtoToCategory(category, api.getImageByCategory(category))

    override fun getPreviewsByCategoryWithPaging(category: String): Flow<PagingData<Image>> {

        val pagingSourceFactory = { dao.imagesByCategory(category) }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            remoteMediator = ImageRemoteMediator(
                category,
                api,
                dao,
                mapper
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
            .map { pagingData ->
                pagingData.map(mapper::mapImageDbToImage)
            }
    }

    override suspend fun getImage(imageUrl: String): ResponseBody =
        api.getImage(imageUrl)

}