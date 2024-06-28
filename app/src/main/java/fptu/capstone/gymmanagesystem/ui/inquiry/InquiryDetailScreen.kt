package fptu.capstone.gymmanagesystem.ui.inquiry

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import fptu.capstone.gymmanagesystem.viewmodel.InquiryViewModel

@Composable
fun InquiryDetailScreen(inquiryViewModel: InquiryViewModel = hiltViewModel(), id: String) {
    val inquiryState by inquiryViewModel.inquiry.collectAsState()
    Text(text = "Inquiry Detail Screen $id")
}