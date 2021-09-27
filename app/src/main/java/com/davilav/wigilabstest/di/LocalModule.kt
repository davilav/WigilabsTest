package com.davilav.wigilabstest.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.davilav.wigilabstest.data.local.db.LocalDataBase
import com.davilav.wigilabstest.utils.Constants
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {
    single { provideRoom(androidApplication()) }
}

private const val DATABASE_NAME = Constants.DATABASE_NAME
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE Movie(id INTEGER PRIMARY KEY NOT NULL, original_title STRING, overview STRING, popularity STRING, poster_path STRING, release_date STRING, title STRING, vote_average STRING, vote_count STRING)"
        )
    }
}

fun provideRoom(application: Application): LocalDataBase = Room.databaseBuilder(
    application.applicationContext,
    LocalDataBase::class.java,
    DATABASE_NAME
).addMigrations(
    MIGRATION_1_2
).build()