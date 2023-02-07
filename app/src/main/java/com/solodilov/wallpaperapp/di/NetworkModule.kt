package com.solodilov.wallpaperapp.di

import com.solodilov.wallpaperapp.data.datasource.network.PixabayApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

	private companion object {

		const val BASE_URL = "https://pixabay.com/api/"
	}

	@Provides
	fun provideGsonConverterFactory(): GsonConverterFactory =
		GsonConverterFactory.create()

	@Provides
	fun provideOkHttpClient(): OkHttpClient =
		OkHttpClient.Builder()
			.addInterceptor(createLoggingInterceptor())
			.build()

	private fun createLoggingInterceptor(): Interceptor =
		HttpLoggingInterceptor()
			.setLevel(HttpLoggingInterceptor.Level.BASIC)

	@Provides
	@Singleton
	fun provideRetrofit(
		client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
	): Retrofit =
		Retrofit.Builder()
			.baseUrl(BASE_URL)
			.client(client)
			.addConverterFactory(gsonConverterFactory)
			.build()

	@Provides
	@Singleton
	fun providePixabayApiApi(
		retrofit: Retrofit,
	): PixabayApi =
		retrofit.create(PixabayApi::class.java)
}