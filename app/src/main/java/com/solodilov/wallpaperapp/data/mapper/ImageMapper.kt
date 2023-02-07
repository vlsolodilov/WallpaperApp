package com.solodilov.wallpaperapp.data.mapper

import com.solodilov.wallpaperapp.data.datasource.database.entity.ImageDb
import com.solodilov.wallpaperapp.data.datasource.network.PixabayApi.Companion.PER_PAGE
import com.solodilov.wallpaperapp.data.model.ImageDto
import com.solodilov.wallpaperapp.data.model.ImageListDto
import com.solodilov.wallpaperapp.domain.entity.Category
import com.solodilov.wallpaperapp.domain.entity.Image
import javax.inject.Inject
import kotlin.random.Random

class ImageMapper @Inject constructor() {

    fun mapImageListDtoToCategory(category: String, imageListDto: ImageListDto): Category =
        Category(
            name = category,
            image = imageListDto.imagesDto[Random.nextInt(PER_PAGE)].webformatURL
        )

    fun mapImageListDtoToImageListDb(category: String, imageListDto: ImageListDto): List<ImageDb> =
        imageListDto.imagesDto.map{ mapImageDtoToImageDb(category, it) }

    fun mapImageDbToImage(imageDb: ImageDb): Image =
        Image(
            id = imageDb.id,
            previewUrl = imageDb.previewUrl,
            url = imageDb.url,
        )

    private fun mapImageDtoToImageDb(category: String, imageDto: ImageDto): ImageDb =
        ImageDb(
            previewUrl = imageDto.previewURL,
            url = imageDto.largeImageURL,
            category = category,
        )

}