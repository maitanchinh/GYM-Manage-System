package fptu.capstone.gymmanagesystem.ui.inquiry

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import fptu.capstone.gymmanagesystem.R
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.viewmodel.InquiryViewModel
import fptu.capstone.gymmanagesystem.viewmodel.ProfileViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun InquiryScreen(
    inquiryViewModel: InquiryViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current
    val inquiriesState by inquiryViewModel.inquiries.collectAsState()
    val inquiryState by inquiryViewModel.inquiry.collectAsState()
    val swipeRefreshState by remember { mutableStateOf(SwipeRefreshState(isRefreshing = inquiriesState is DataState.Idle)) }
    val showAddInquiryDialog by inquiryViewModel.showAddInquiryDialog.collectAsState()
    val title by inquiryViewModel.title.collectAsState()
    val message by inquiryViewModel.message.collectAsState()
    val userId = profileViewModel.getUser()?.id!!
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { inquiryViewModel.showOrHideAddInquiryDialog() },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.round_add_24),
                    contentDescription = "Add Inquiry"
                )
            }
        },
        content = { paddingValues ->
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { inquiryViewModel.refreshInquiries() },
            ) {
                when (inquiriesState) {
                    is DataState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is DataState.Success -> {
                        val inquiries =
                            (inquiriesState as? DataState.Success)?.data?.inquiries ?: emptyList()
                        if (inquiries.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "No inquiries found")
                            }
                            return@SwipeRefresh
                        }
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(inquiries.size) {
                                InquiryItem(inquiries, it, navController)
                            }
                        }
                    }

                    is DataState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = (inquiriesState as DataState.Error).message)
                        }
                    }

                    else -> {
                        // Idle
                    }
                }
            }
            if (showAddInquiryDialog) {
                AddInquiryDialog(
                    inquiryViewModel,
                    title,
                    message,
                    inquiryState,
                    inquiriesState,
                    context,
                    userId
                )
            }
        })
}



