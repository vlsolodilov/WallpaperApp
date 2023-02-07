package com.solodilov.wallpaperapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solodilov.wallpaperapp.presentation.common.ViewModelFactory
import com.solodilov.wallpaperapp.presentation.screen.categorylist.CategoryListViewModel
import com.solodilov.wallpaperapp.presentation.screen.image.ImageViewModel
import com.solodilov.wallpaperapp.presentation.screen.imagelist.ImageListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CategoryListViewModel::class)
    fun bindCategoryListViewModel(viewModel: CategoryListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ImageListViewModel::class)
    fun bindImageListViewModel(viewModel: ImageListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ImageViewModel::class)
    fun bindImageViewModel(viewModel: ImageViewModel): ViewModel
}