package com.cp.purecolor2

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.cp.purecolor2.data.Colors
import com.cp.purecolor2.data.DataRepository

class FavoritesViewModel(private val repository: DataRepository): ViewModel() {
    private lateinit var favs: LiveData<List<Colors>>

    fun initialize() {
        favs = repository.getFavs()
    }

    fun getFaves(): LiveData<List<Colors>>{
        return favs
    }

    fun saveFav(color: Colors){
        repository.saveFavs(color)
    }

    class Factory(val repository: DataRepository): ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FavoritesViewModel(repository) as T
        }
    }
}

