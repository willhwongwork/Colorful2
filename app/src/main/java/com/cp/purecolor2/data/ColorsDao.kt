package com.cp.purecolor2.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface ColorsDao {
    @Query("SELECT * FROM colors")
    fun getFavs(): LiveData<List<Colors>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavs(color: Colors)

    @Query("SELECT * FROM colors WHERE name = :colorName")
    fun isFaves(colorName: String): List<Colors>

    @Delete()
    fun deleteFaves(color: Colors)
}