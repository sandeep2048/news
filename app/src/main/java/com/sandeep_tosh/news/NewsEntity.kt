package com.sandeep_tosh.news

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NEWS")
data class NewsEntity(

    @PrimaryKey
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "content") var content: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "publisher") var publisher: String,
    @ColumnInfo(name = "url") var url: String
)