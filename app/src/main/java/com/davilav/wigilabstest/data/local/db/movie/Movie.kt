package com.davilav.wigilabstest.data.local.db.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "original_title") val original_title: String?,
    @ColumnInfo(name = "overview") val overview: String?,
    @ColumnInfo(name = "popularity") val popularity: String?,
    @ColumnInfo(name = "poster_path") val poster_path: String?,
    @ColumnInfo(name = "release_date") val progress: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "vote_average") val vote_average: String?,
    @ColumnInfo(name = "vote_count") val vote_count: String?
)