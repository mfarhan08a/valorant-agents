package com.mfarhan08a.valorantagents.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object DetailAgent : Screen("home/{agentId}") {
        fun createRoute(agentId: Long) = "home/$agentId"
    }
}