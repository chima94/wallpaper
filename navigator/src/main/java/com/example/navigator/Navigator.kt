package com.example.navigator

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.flow.Flow

interface Navigator {

    fun navigateUp(): Boolean
    fun navigatePop(route: String): Boolean
    fun navigateActivity(): Boolean
    fun navigate(route: String, builder: NavOptionsBuilder.() -> Unit = {launchSingleTop = true}): Boolean
    val destination: Flow<NavigatorEvent>
}