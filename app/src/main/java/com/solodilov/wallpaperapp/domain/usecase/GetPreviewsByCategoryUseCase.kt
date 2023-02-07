package com.solodilov.wallpaperapp.domain.usecase

import androidx.paging.PagingData
import com.solodilov.wallpaperapp.domain.entity.Image
import com.solodilov.wallpaperapp.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPreviewsByCategoryUseCase @Inject constructor(private val imageRepository: ImageRepository) {

    operator fun invoke(category: String): Flow<PagingData<Image>> =
        imageRepository.getPreviewsByCategoryWithPaging(category)

}