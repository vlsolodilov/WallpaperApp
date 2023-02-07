package com.solodilov.wallpaperapp.di

import android.app.Application
import com.solodilov.wallpaperapp.data.datasource.ImageRemoteDatasource
import com.solodilov.wallpaperapp.data.datasource.ImageRemoteDatasourceImpl
import com.solodilov.wallpaperapp.data.datasource.database.ImageDao
import com.solodilov.wallpaperapp.data.datasource.database.ImageDatabase
import com.solodilov.wallpaperapp.data.repository.ImageRepositoryImpl
import com.solodilov.wallpaperapp.domain.repository.ImageRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface DataModule {

	@Singleton
	@Binds
	fun bindImageRemoteDatasource(impl: ImageRemoteDatasourceImpl): ImageRemoteDatasource

	@Singleton
	@Binds
	fun bindImageRepository(impl: ImageRepositoryImpl): ImageRepository

	companion object {

		@Singleton
		@Provides
		fun provideImageDatabase(application: Application): ImageDatabase {
			return ImageDatabase.getInstance(application)
		}

		@Singleton
		@Provides
		fun provideImageDao(imageDatabase: ImageDatabase): ImageDao {
			return imageDatabase.imageDao()
		}
	}
}