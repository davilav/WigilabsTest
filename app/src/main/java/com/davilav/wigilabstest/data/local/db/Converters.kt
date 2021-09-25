package com.davilav.wigilabstest.data.local.db

import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Calendar? = value?.let { value ->
        GregorianCalendar().also { calendar ->
            calendar.timeInMillis = value
        }
    }

    @TypeConverter
    fun toTimestamp(timestamp: Calendar?): Long? = timestamp?.timeInMillis
}