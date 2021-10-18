package com.example.religiondestination

import com.example.navigator.NavigationDestination

object ReligionDestination: NavigationDestination {

    private const val Religion_DESTINATION = "religion"
    override fun route() = Religion_DESTINATION
}