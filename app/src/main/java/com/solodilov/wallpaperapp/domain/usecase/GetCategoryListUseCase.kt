package com.solodilov.wallpaperapp.domain.usecase

import com.solodilov.wallpaperapp.domain.entity.Category
import com.solodilov.wallpaperapp.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoryListUseCase @Inject constructor(private val imageRepository: ImageRepository) {

    operator fun invoke(): Flow<List<Category>> =
        imageRepository.getCategoryList()
}