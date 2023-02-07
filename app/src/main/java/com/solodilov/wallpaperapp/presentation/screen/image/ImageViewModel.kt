package com.solodilov.wallpaperapp.presentation.screen.image

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodilov.wallpaperapp.domain.entity.Category
import com.solodilov.wallpaperapp.domain.usecase.GetImageUseCase
import com.solodilov.wallpaperapp.presentation.common.UiState
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import com.solodilov.wallpaperapp.presentation.common.Result

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class ImageViewModel @Inject constructor(
    private val getImageUseCase: GetImageUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Bitmap>>(UiState.Loading)
    val uiState: StateFlow<UiState<Bitmap>> = _uiState

    private val _imageUrl = MutableStateFlow("")
    private val imageUrl: StateFlow<String> = _imageUrl.asStateFlow()

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            imageUrl
                .flatMapLatest {
                    flow {
                        emit(Result.Loading)
                        try {
                            emit(Result.Success(getImageUseCase(it)))
                        } catch (e: Exception) {
                            emit(Result.Error(e))
                        }
                    }
                }.collect { result ->
                    _uiState.value = when (result) {
                        is Result.Loading -> UiState.Loading
                        is Result.Success -> UiState.Success(result.data)
                        is Result.Error -> UiState.Error(result.exception)
                    }
                }
        }
    }

    fun setImageUrl(imageUrl: String) {
        _imageUrl.value = imageUrl
    }

}