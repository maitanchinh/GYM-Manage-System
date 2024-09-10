package fptu.capstone.gymmanagesystem.ui.navigation

sealed class Route(val route: String) {
    object Login : Route("login")
    object Dashboard : Route("dashboard")
    object Profile : Route("profile")
    object Home : Route("home")
    object Schedule : Route("schedule")
    object Signup : Route("signup")
    object ProfileDetail : Route("profileDetail/{id}"){
        fun createRouteWithId(id: String): String {
            return "profileDetail/$id"
        }
    }
    object Course : Route("course")
    object CourseDetail : Route("courseDetail/{id}"){
        fun createRouteWithId(id: String): String {
            return "courseDetail/$id"
        }
    }
    object Class : Route("class")
    object AllCourse : Route("course/allCourse")
    object ClassDetail : Route("schedule/classDetail/{courseId}/{classId}"){
        fun createRouteWithId(courseId: String, classId: String): String {
            return "schedule/classDetail/$courseId/$classId"
        }
    }
    object Inquiry : Route("inquiry")
    object InquiryDetail : Route("inquiryDetail/{id}"){
        fun createRouteWithId(id: String): String {
            return "inquiryDetail/$id"
        }
    }
    object Transaction : Route("profile/transaction")

}