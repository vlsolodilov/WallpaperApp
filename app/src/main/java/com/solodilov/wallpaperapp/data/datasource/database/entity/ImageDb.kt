package com.solodilov.wallpaperapp.data.datasource.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class ImageDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int = UNDEFINED_ID,
    val previewUrl: String,
    val url: String,
    val category: String,
){
    companion object {
        const val UNDEFINED_ID = 0
    }
}
