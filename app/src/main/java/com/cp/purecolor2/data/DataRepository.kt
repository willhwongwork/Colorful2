package com.cp.purecolor2.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import java.io.InputStream
import java.util.concurrent.Executor

public class DataRepository(val context: Context, val colorsDao: ColorsDao, val executor: Executor) {

    fun getColors(): LiveData<List<Colors>> {
        val data: MutableLiveData<List<Colors>> = MutableLiveData()
        data.value = parseColorAsset()
        return data
    }

    fun getFavs(): LiveData<List<Colors>> {
        return colorsDao.getFavs()
    }

    fun saveFavs(color: Colors) {
        executor.execute { colorsDao.insertFavs(color) }
    }

    fun isFaves(color: Colors): Boolean{
        var contained = false
        executor.execute {  val faves = colorsDao.isFaves(color.name)
            contained =  faves.contains(color)
        }
        return contained
    }

    fun deleteFaves(color: Colors) {
        executor.execute { colorsDao.deleteFaves(color) }
    }

    private fun parseColorAsset(): List<Colors> {
        val inputStream: InputStream = context.assets.open("colors_dl.xml")
        Log.d("DataRepository", "inputStream opened" )
        return XMLParser.parse(inputStream)
    }

}