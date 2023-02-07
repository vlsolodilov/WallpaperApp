package com.solodilov.wallpaperapp.di

import android.app.Application
import com.solodilov.wallpaperapp.presentation.screen.categorylist.CategoryListFragment
import com.solodilov.wallpaperapp.presentation.screen.image.ImageFragment
import com.solodilov.wallpaperapp.presentation.screen.imagelist.ImageListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
	DataModule::class,
	NetworkModule::class,
	ViewModelModule::class,
])
interface ApplicationComponent {

	fun inject(fragment: CategoryListFragment)
	fun inject(fragment: ImageListFragment)
	fun inject(fragment: ImageFragment)

	@Component.Factory
	interface Factory {
		fun create(
			@BindsInstance application: Application,
		): ApplicationComponent
	}
}