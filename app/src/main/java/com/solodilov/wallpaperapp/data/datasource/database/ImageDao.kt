package com.solodilov.wallpaperapp.data.datasource.database

import androidx.paging.PagingSource
import androidx.room.*
import com.solodilov.wallpaperapp.data.datasource.database.entity.ImageDb

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<ImageDb>)

    @Query("SELECT * FROM images WHERE category LIKE :category")
    fun imagesByCategory(category: String): PagingSource<Int, ImageDb>

    @Query("DELETE FROM images")
    suspend fun clearImages()

    @Transaction
    suspend fun refreshImages(images: List<ImageDb>) {
        clearImages()
        insertAll(images)
    }
}
