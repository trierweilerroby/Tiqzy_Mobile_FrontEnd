package com.example.tiqzy_mobile_frontend.ui.navigation

sealed class Screen(val route: String, val title: String) {
    object Onboarding : Screen("onboarding", "Onboarding")
    object OnboardingName : Screen ("onboardingName","OnboardingName")
    object OnboardingCategories : Screen ("onboardingCategories","OnboardingCategories")
    object Login : Screen("login", "Login")
    object Signup: Screen("signup", "Signup")
    object Home : Screen("home", "Home")
    object Tickets : Screen("tickets", "Tickets")
    object Favorites : Screen("favorites", "Favorites")
    object Profile : Screen("profile", "Profile")
    object EventList : Screen("eventList", "EventList")
    object Event : Screen("event", "Event")


}
