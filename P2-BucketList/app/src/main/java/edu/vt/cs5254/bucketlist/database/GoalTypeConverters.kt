package edu.vt.cs5254.bucketlist.database

import androidx.room.TypeConverter
import java.util.Date

class GoalTypeConverters {
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch)
    }
}