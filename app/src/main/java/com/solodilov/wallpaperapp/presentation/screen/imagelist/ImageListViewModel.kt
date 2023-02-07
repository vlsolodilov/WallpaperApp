package com.solodilov.wallpaperapp.presentation.screen.imagelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.solodilov.wallpaperapp.domain.entity.Image
import com.solodilov.wallpaperapp.domain.usecase.GetPreviewsByCategoryUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ImageListViewModel @Inject constructor(
    private val getPreviewsByCategoryUseCase: GetPreviewsByCategoryUseCase,
) : ViewModel() {

    private val _category = MutableStateFlow("")
    val category: StateFlow<String> = _category.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val data: StateFlow<PagingData<Image>> = category
        .flatMapLatest { getPreviewsByCategoryUseCase(it) }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun setCategory(category: String) {
        _category.value = category
    }

}