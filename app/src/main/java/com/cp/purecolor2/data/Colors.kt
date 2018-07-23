package com.cp.purecolor2.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "colors",
        indices = arrayOf(Index(value = "name", unique = true)))
data class Colors(
        @ColumnInfo(name = "name")
        val name: String,
        val hex: String,
        val rgb: ArrayList<String>) {
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null
}