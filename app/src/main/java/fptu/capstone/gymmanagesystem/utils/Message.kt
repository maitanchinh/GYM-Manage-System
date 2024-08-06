package fptu.capstone.gymmanagesystem.utils

enum class Message(val message: String) {
    ERROR_NETWORK("Network error, Please check your connection"),
    FETCH_DATA_FAILURE("Failed to fetch data, please try again"),
    PAYMENT_PENDING("Payment is pending, please wait for a moment"),
}