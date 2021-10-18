package com.example.navigator

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class NavigatorImpl @Inject constructor(): Navigator {

    private val navigationEvents = Channel<NavigatorEvent>()
    override val destination = navigationEvents.receiveAsFlow()

    override fun navigateUp(): Boolean {
        return navigationEvents.trySend(NavigatorEvent.NavigateUp).isSuccess
    }

    override fun navigate(route: String, builder: NavOptionsBuilder.() -> Unit): Boolean {
        return navigationEvents.trySend(NavigatorEvent.Directions(route, builder)).isSuccess
    }


    override fun navigatePop(route: String): Boolean {
        return navigationEvents.trySend(NavigatorEvent.NavigatePop(route)).isSuccess
    }

    override fun navigateActivity(): Boolean {
        return navigationEvents.trySend(NavigatorEvent.NavigateActivity).isSuccess
    }
}