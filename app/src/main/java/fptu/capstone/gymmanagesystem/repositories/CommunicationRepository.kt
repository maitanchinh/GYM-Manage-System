package fptu.capstone.gymmanagesystem.repositories

import fptu.capstone.gymmanagesystem.model.Communication
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.network.CommentRequestBody
import fptu.capstone.gymmanagesystem.network.CommunicationApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class CommunicationRepository @Inject constructor(private val communicationApiService: CommunicationApiService){
    suspend fun getCommunications(filterRequestBody: FilterRequestBody) = communicationApiService.getCommunications(filterRequestBody)

    suspend fun postCommunication(classId: String, message: String, imageFile: File?) : Communication {
        val messageBody = message.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }

        val imagePath = imageFile?.let {
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), it)
            MultipartBody.Part.createFormData("imageUrl", it.name, requestFile)
        }

        return  communicationApiService.postCommunication(classId = classId, imageUrl = imagePath, message = messageBody)
    }

    suspend fun sendComment(message: String, communicationId: String) = communicationApiService.sendComment(communicationId = communicationId, body = CommentRequestBody(message))
}