package com.example.bottomnavigation

import androidx.annotation.StringRes

sealed class BottomNavigationEntry(val route: String, @StringRes val resourceID: Int){
    companion object{
        const val RELIGION = "religion"
        const val NATURE = "nature"
        const val ART = "ART"
        const val SEARCH = "SEARCH"
    }
}