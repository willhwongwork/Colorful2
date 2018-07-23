package com.cp.purecolor2

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.cp.purecolor2.data.Colors
import com.cp.purecolor2.data.DataRepository

class CollectionViewModel(private val repository: DataRepository): ViewModel() {
    private lateinit var colors: LiveData<List<Colors>>

    fun initialize() {
        colors = repository.getColors()
    }

    fun getColors(): LiveData<List<Colors>> {
        return colors
    }

    class Factory(val repository: DataRepository) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CollectionViewModel(repository) as T
        }
    }

}