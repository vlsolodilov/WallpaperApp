package com.solodilov.wallpaperapp.data.model


import com.google.gson.annotations.SerializedName

data class ImageListDto(
    @SerializedName("hits")
    val imagesDto: List<ImageDto>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalHits")
    val totalImages: Int
)