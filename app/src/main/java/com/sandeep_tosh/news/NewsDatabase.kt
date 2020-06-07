package com.sandeep_tosh.news

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(NewsEntity::class), version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}