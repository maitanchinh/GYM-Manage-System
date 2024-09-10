package fptu.capstone.gymmanagesystem.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import fptu.capstone.gymmanagesystem.model.FilterRequestBody
import fptu.capstone.gymmanagesystem.ui.component.Gap
import fptu.capstone.gymmanagesystem.ui.theme.Amber
import fptu.capstone.gymmanagesystem.ui.theme.ForestGreen
import fptu.capstone.gymmanagesystem.utils.DataState
import fptu.capstone.gymmanagesystem.utils.parseDateTime
import fptu.capstone.gymmanagesystem.viewmodel.TransactionViewModel
import fptu.capstone.gymmanagesystem.viewmodel.UserViewModel
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun TransactionScreen(
    transactionViewModel: TransactionViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    val transactionsState = transactionViewModel.transactions.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    LaunchedEffect(Unit) {
        transactionViewModel.getTransactions(FilterRequestBody(memberId = userViewModel.getUser()!!.id))
    }
    SwipeRefresh(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        state = swipeRefreshState, onRefresh = {
            isRefreshing = true
            transactionViewModel.getTransactions(FilterRequestBody(memberId = userViewModel.getUser()!!.id))
            isRefreshing = false
        }) { }
    when (val state = transactionsState.value) {
        is DataState.Success -> {
            val transactions = state.data.data
            Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                transactions.forEach { transaction ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(16.dp))
                            .background(color = MaterialTheme.colorScheme.secondaryContainer)
                            .padding(16.dp)
                    ) {
                        Column {
                            Text(
                                text = transaction.status!!,
                                style = MaterialTheme.typography.titleLarge,
                                color = if (transaction.status == "Pending") Amber else if (transaction.status == "Successful") ForestGreen else MaterialTheme.colorScheme.error
                            )
                            Gap.k8.Height()
                            Text(
                                text = parseDateTime(transaction.createAt!!).format(
                                    DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")
                                ).toString(),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Gap.k8.Height()
                            Text(
                                text = NumberFormat.getNumberInstance(Locale.US).format(transaction.amount) + " VND",
                                style = MaterialTheme.typography.bodyMedium)
                            Gap.k16.Height()
                            Text(
                                text = "Package",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Gap.k8.Height()
                            Text(
                                text = transaction.pack!!.name!!,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                    }
                }

            }
        }

        is DataState.Error -> {
            val message = state.message
            Text(text = message)
        }

        else -> {}

    }
}