package com.example.searchresultdestination

import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument
import com.example.navigator.NavigationDestination

object SearchResultDestination: NavigationDestination{
    override fun route(): String = SEARCH_RESULT_NAV_ROUTE

    override val arguments: List<NamedNavArgument>
        get() = listOf(navArgument(SEARCH_RESULT_PARAM){type =  NavType.StringType})

    const val SEARCH_RESULT_PARAM = "search_query"

    private const val SEARCH_RESULT_ROUTE = "search_result"
    private const val SEARCH_RESULT_NAV_ROUTE = "$SEARCH_RESULT_ROUTE/{$SEARCH_RESULT_PARAM}"
    fun createSearchRoute(query: String) = "$SEARCH_RESULT_ROUTE/${query}"

}