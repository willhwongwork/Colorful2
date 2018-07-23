package com.cp.purecolor2

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MainFragmentPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    companion object {
        val PAGE_COUNT = 3
    }
    override fun getItem(position: Int): Fragment? {
        when(position) {
            0 -> return FavoriteFragment.newInstance(position + 1)
            1 -> return CollectionFragment.newInstance(position + 1)
            2 -> return ColorPickerFragment.newInstance(position + 1)
            else -> {
                return null
            }
        }
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }
}