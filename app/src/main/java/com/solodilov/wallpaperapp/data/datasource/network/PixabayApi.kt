package com.solodilov.wallpaperapp.data.datasource.network

import com.solodilov.wallpaperapp.data.model.ImageListDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

interface PixabayApi {

    companion object {
        const val KEY = "33106230-b104905cd7ff74ed17e2229af"
        const val ORDER = "latest"
        const val PER_PAGE = 20
        const val STARTING_PAGE_INDEX = 1
    }

    @GET("?key=$KEY&order=$ORDER&per_page=$PER_PAGE")
    suspend fun getImageByCategory(@Query("category") category: String): ImageListDto

    @GET("?key=$KEY")
    suspend fun getImagesByCategoryWithPaging(
        @Query("category") category: String,
        @Query("page") page: Int?,
        @Query("per_page") itemsPerPage: Int,
    ): ImageListDto

    @Streaming
    @GET
    suspend fun getImage(@Url url:String): ResponseBody
}