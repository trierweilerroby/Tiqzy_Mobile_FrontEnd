package com.example.tiqzy_mobile_frontend.ui.navigation

sealed class Screen(val route: String, val title: String) {
    object Onboarding : Screen("onboarding", "Onboarding")
    object Home : Screen("home", "Home")
    object Tickets : Screen("tickets", "Tickets")
    object Favorites : Screen("favorites", "Favorites")
    object Profile : Screen("profile", "Profile")
    object Login : Screen("login", "Login")
    object Signup: Screen("signup", "Signup")
}
