package com.example.naturedestination

import com.example.navigator.NavigationDestination

object NatureDestination: NavigationDestination {

    private const val Nature_DESTINATION = "nature"
    override fun route() = Nature_DESTINATION
}