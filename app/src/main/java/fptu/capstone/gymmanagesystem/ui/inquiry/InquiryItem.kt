package fptu.capstone.gymmanagesystem.ui.inquiry

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fptu.capstone.gymmanagesystem.R
import fptu.capstone.gymmanagesystem.model.Inquiry
import fptu.capstone.gymmanagesystem.ui.component.Gap
import fptu.capstone.gymmanagesystem.ui.navigation.Route
import fptu.capstone.gymmanagesystem.ui.theme.ForestGreen
import fptu.capstone.gymmanagesystem.ui.theme.GoldYellow

@Composable
fun InquiryItem(
    inquiries: List<Inquiry>,
    index: Int,
    navController: NavHostController,
    isLoading: Boolean = false
) {
    if (isLoading) {
        Box(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 16.dp), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(modifier = Modifier.padding(8.dp))
        }
    } else
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .clip(shape = RoundedCornerShape(16.dp))
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                .padding(16.dp)
                .clickable {
                    navController.navigate(Route.InquiryDetail.createRouteWithId(inquiries[index].id!!))
                }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (inquiries[index].status.equals("Accepted")) {
                    Icon(
                        painter = painterResource(id = R.drawable.round_check_circle_outline_32),
                        tint = ForestGreen,
                        contentDescription = "Received Reply"
                    )
                } else if (inquiries[index].status.equals("Pending")) {
                    Icon(
                        painter = painterResource(id = R.drawable.round_pending_32),
                        tint = GoldYellow,
                        contentDescription = "Received Reply"
                    )
                } else if (inquiries[index].status.equals("Rejected")) {
                    Icon(
                        painter = painterResource(id = R.drawable.round_highlight_off_32),
                        tint = MaterialTheme.colorScheme.error,
                        contentDescription = "Received Reply"
                    )
                }
                Gap.k16.Width()
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = inquiries[index].title!!,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = inquiries[index].message!!,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Gap.k16.Width()
                if (inquiries[index].inquiryResponse != null) {
                    Icon(
                        painter = painterResource(id = R.drawable.round_mark_email_read_24),
                        tint = Color(0xFF228B22),
                        contentDescription = "Received Reply"
                    )
                }
            }
        }
}