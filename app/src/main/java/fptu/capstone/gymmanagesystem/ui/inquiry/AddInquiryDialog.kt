package fptu.capstone.gymmanagesystem.ui.inquiry

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fptu.capstone.gymmanagesystem.model.Inquiries
import fptu.capstone.gymmanagesystem.model.Inquiry
import fptu.capstone.gymmanagesystem.model.InquiryRequestBody
import fptu.capstone.gymmanagesystem.ui.component.Gap
import fptu.capstone.gymmanagesystem.ui.component.TextField
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.viewmodel.InquiryViewModel

@Composable
fun AddInquiryDialog(
    inquiryViewModel: InquiryViewModel,
    title: String,
    message: String,
    inquiryState: DataState<Inquiry>,
    inquiriesState: DataState<Inquiries>,
    context: Context,
    userId: String
) {
    AlertDialog(
        onDismissRequest = { inquiryViewModel.showOrHideAddInquiryDialog() },
        title = {
            Text(text = "Add Inquiry")
        },
        text = {
            Column {
                TextField(
                    label = "Title",
                    value = title,
                    onTextChange = { inquiryViewModel.setTitle(it) }
                )
                Gap.k16.Height()
                TextField(
                    label = "Message",
                    value = message,
                    onTextChange = { inquiryViewModel.setMessage(it) },
                    maxLines = 5,
                )
                Gap.k16.Height()
                when (inquiryState) {
                    is DataState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is DataState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = (inquiriesState as DataState.Error).message + "/nPlease try again later")
                        }
                    }

                    is DataState.Success -> {
                        inquiryViewModel.showOrHideAddInquiryDialog()
                        inquiryViewModel.setTitle("")
                        inquiryViewModel.setMessage("")
                        inquiryViewModel.resetInquiry()
                        inquiryViewModel.refreshInquiries()
                        LaunchedEffect(Unit) {
                            Toast.makeText(
                                context,
                                "Inquiry created successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    else -> {
                        // Idle
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                inquiryViewModel.createInquiry(
                    InquiryRequestBody(
                        title = title,
                        message = message,
                        memberId = userId
                    )
                )
            }) {
                Text(text = "Save")
            }
        },
        dismissButton = {
            TextButton(onClick = { inquiryViewModel.showOrHideAddInquiryDialog() }) {
                Text(text = "Cancel")
            }
        },
    )
}