package fptu.capstone.gymmanagesystem.ui.inquiry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fptu.capstone.gymmanagesystem.R
import fptu.capstone.gymmanagesystem.ui.component.Gap
import fptu.capstone.gymmanagesystem.ui.theme.ForestGreen
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.viewmodel.InquiryViewModel

@Composable
fun InquiryDetailScreen(inquiryViewModel: InquiryViewModel = hiltViewModel(), id: String) {
    val inquiryState by inquiryViewModel.inquiry.collectAsState()
    LaunchedEffect(Unit) {
        inquiryViewModel.getInquiryDetail(id)
    }

    when (inquiryState) {
        is DataState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is DataState.Success -> {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                item {
                    val inquiry = (inquiryState as DataState.Success).data
                    Text(text = inquiry.title!!, style = MaterialTheme.typography.headlineLarge)
                    Gap.k16.Height()
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(shape = RoundedCornerShape(16.dp))
                            .background(color = MaterialTheme.colorScheme.secondaryContainer)
                            .padding(16.dp)
                    ) {
                        Text(text = inquiry.message!!, style = MaterialTheme.typography.bodyLarge)
                    }
                    if (inquiry.inquiryResponse != null) {
                        Gap.k8.Height()
                        if (inquiry.status.equals("Accepted")) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.round_check_circle_outline_32),
                                    contentDescription = null,
                                    tint = ForestGreen
                                )
                            }
                        } else if (inquiry.status.equals("Rejected")) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.round_highlight_off_32),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                        Gap.k8.Height()
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(shape = RoundedCornerShape(16.dp))
                                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = inquiry.inquiryResponse?.message!!,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }

        is DataState.Error -> {
            // Error
        }

        else -> {
            // Idle
        }
    }
}