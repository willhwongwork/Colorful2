package com.cp.purecolor2.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context

@Database(entities = arrayOf(Colors::class), version = 1)
@TypeConverters(Converters::class)
abstract class ColorDatabase: RoomDatabase() {
    abstract fun colorsDao(): ColorsDao

    companion object {
        @Volatile private var INSTANCE: ColorDatabase? = null

        fun getInstance(context: Context): ColorDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        ColorDatabase::class.java, "Sample.db")
                        .build()
    }

}