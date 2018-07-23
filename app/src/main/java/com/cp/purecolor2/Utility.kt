package com.cp.purecolor2

import android.content.Context


class Utility {
    companion object {
        fun calculateNoOfColumns(context: Context, columnWidthDp: Int): Int {
            val displayMetrics = context.resources.getDisplayMetrics()
            val dpWidth = displayMetrics.widthPixels / displayMetrics.density
            return (dpWidth / columnWidthDp).toInt()
        }
    }
}