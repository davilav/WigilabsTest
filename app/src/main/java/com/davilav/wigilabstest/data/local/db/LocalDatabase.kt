package com.davilav.wigilabstest.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.davilav.wigilabstest.data.local.db.movie.Movie
import com.davilav.wigilabstest.data.local.db.movie.MovieDao

@Database(
    entities = [Movie::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class LocalDataBase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}