package fptu.capstone.gymmanagesystem.model

data class AuthResponse(
    val token: String
)

data class LoginRequest(
    val email: String,
    val password: String
)