package com.solodilov.wallpaperapp.presentation.screen.categorylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodilov.wallpaperapp.domain.entity.Category
import com.solodilov.wallpaperapp.domain.usecase.GetCategoryListUseCase
import com.solodilov.wallpaperapp.presentation.common.UiState
import com.solodilov.wallpaperapp.presentation.common.asResult
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import com.solodilov.wallpaperapp.presentation.common.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryListViewModel @Inject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Category>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Category>>> = _uiState

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            getCategoryListUseCase()
                .asResult()
                .collect { result ->
                    _uiState.value = when (result) {
                        is Result.Loading -> UiState.Loading
                        is Result.Success -> UiState.Success(result.data)
                        is Result.Error -> UiState.Error(result.exception)
                    }
                }
        }
    }

}