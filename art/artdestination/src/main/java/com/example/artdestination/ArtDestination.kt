package com.example.artdestination

import com.example.navigator.NavigationDestination

object ArtDestination: NavigationDestination {

    private const val Art_DESTINATION = "art"
    override fun route() = Art_DESTINATION
}