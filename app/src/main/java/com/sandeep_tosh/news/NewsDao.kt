package com.sandeep_tosh.news

import androidx.room.*

@Dao
interface NewsDao {
    @Query("SELECT * FROM NEWS")
    fun getAll(): List<NewsEntity>


    @Query("DELETE FROM NEWS")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( newsEntity: List<NewsEntity> )




}