package com.example.searchdestination

import com.example.navigator.NavigationDestination

object SearchDestination: NavigationDestination {

    private const val Search_DESTINATION = "search"
    override fun route() = Search_DESTINATION
}