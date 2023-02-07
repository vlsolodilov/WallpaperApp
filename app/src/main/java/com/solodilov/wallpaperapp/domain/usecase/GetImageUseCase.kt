package com.solodilov.wallpaperapp.domain.usecase

import android.graphics.Bitmap
import androidx.paging.PagingData
import com.solodilov.wallpaperapp.domain.entity.Image
import com.solodilov.wallpaperapp.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImageUseCase @Inject constructor(private val imageRepository: ImageRepository) {

    suspend operator fun invoke(imageUrl: String): Bitmap =
        imageRepository.getImage(imageUrl)

}