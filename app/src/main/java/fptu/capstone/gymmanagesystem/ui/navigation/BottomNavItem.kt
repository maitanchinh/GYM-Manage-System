package fptu.capstone.gymmanagesystem.ui.navigation

import fptu.capstone.gymmanagesystem.R

sealed class BottomNavItem(var title: String, var icon: Int, var route: String) {
    object Schedule : BottomNavItem("Schedule", R.drawable.round_calendar_month_24, Route.Schedule.route)
    object Profile : BottomNavItem("Profile", R.drawable.round_person_24, Route.Profile.route)
    object Class : BottomNavItem("Class", R.drawable.round_class_24, Route.Class.route)
    object Inquiry  : BottomNavItem("Inquiry", R.drawable.round_mail_24, Route.Inquiry.route)
}