package com.cp.purecolor2

import android.content.Context
import com.cp.purecolor2.data.ColorDatabase
import com.cp.purecolor2.data.ColorsDao
import java.util.concurrent.Executor
import java.util.concurrent.Executors

object Injection {

    fun provideUserDataSource(context: Context): ColorsDao {
        val database = ColorDatabase.getInstance(context)
        return database.colorsDao()
    }

    fun provideExecutor(): Executor {
        val executor = Executors.newSingleThreadExecutor()
        return executor
    }
}