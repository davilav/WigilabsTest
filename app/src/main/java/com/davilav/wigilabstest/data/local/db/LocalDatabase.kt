package com.davilav.wigilabstest.data.local.db

@Database(
    entities = [],
    version = 1
)
@TypeConverters(Converters::class)
abstract class LocalDataBase : RoomDatabase() {
}