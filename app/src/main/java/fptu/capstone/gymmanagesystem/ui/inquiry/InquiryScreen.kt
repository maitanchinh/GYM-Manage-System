package fptu.capstone.gymmanagesystem.ui.inquiry

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import fptu.capstone.gymmanagesystem.R
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.viewmodel.InquiryViewModel
import fptu.capstone.gymmanagesystem.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun InquiryScreen(
    inquiryViewModel: InquiryViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current
    val inquiriesState by inquiryViewModel.inquiries.collectAsState()
    val inquiryState by inquiryViewModel.inquiry.collectAsState()
    val deleteState by inquiryViewModel.deleteState.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState by remember { mutableStateOf(SwipeRefreshState(isRefreshing = isRefreshing)) }
    val showAddInquiryDialog by inquiryViewModel.showAddInquiryDialog.collectAsState()
    val title by inquiryViewModel.title.collectAsState()
    val message by inquiryViewModel.message.collectAsState()
    val userId = userViewModel.getUser()?.id!!
    LaunchedEffect(Unit) {
        inquiryViewModel.fetchInquiries(FilterRequestBody(memberId = userViewModel.getUser()!!.id))
        println("Member ID: ${userViewModel.getUser()!!.id}")
    }
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
                onRefresh = { inquiryViewModel.refreshInquiries(FilterRequestBody(memberId = userViewModel.getUser()!!.id)) },
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
                            (inquiriesState as? DataState.Success)?.data?.data ?: emptyList()
                        if (inquiries.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "No inquiries found")
                            }

                        }
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(inquiries.size, key = { inquiries[it].id!! }) { index ->
//                                val swipeableState  = rememberSwipeableState(0)
                                val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
                                    confirmValueChange = {
                                        if (it == SwipeToDismissBoxValue.EndToStart) {
                                            inquiryViewModel.deleteInquiry(inquiries[index].id!!)
                                        }
                                        true
                                    }
                                )

                                SwipeToDismissBox(
                                    state = swipeToDismissBoxState,
                                    enableDismissFromEndToStart = true,
                                    enableDismissFromStartToEnd = false,
                                    backgroundContent = {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(32.dp),
                                            contentAlignment = Alignment.CenterEnd
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.round_delete_24),
                                                contentDescription = "Delete Inquiry",
                                                tint = MaterialTheme.colorScheme.error
                                            )
                                        }
                                    },
                                    content = {
                                        InquiryItem(inquiries, index, navController, deleteState is DataState.Loading)
                                    }
                                )
                            }
                        }
                    }

                    is DataState.Error -> {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(paddingValues),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = (inquiriesState as DataState.Error).message,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
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
            when (deleteState) {
                is DataState.Success -> {
                    Toast.makeText(context, "Inquiry deleted successfully", Toast.LENGTH_SHORT).show()
                    inquiryViewModel.resetDeleteState()
                }
                is DataState.Error -> {
                    Toast.makeText(context, (deleteState as DataState.Error).message, Toast.LENGTH_SHORT).show()
                    inquiryViewModel.resetDeleteState()
                }
                else -> {
                    // Do nothing for other states
                }
            }
        })
}



