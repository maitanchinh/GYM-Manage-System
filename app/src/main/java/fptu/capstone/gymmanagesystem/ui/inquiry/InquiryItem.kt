package fptu.capstone.gymmanagesystem.ui.inquiry

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fptu.capstone.gymmanagesystem.R
import fptu.capstone.gymmanagesystem.model.Inquiry
import fptu.capstone.gymmanagesystem.ui.component.Gap
import fptu.capstone.gymmanagesystem.ui.navigation.Route

@Composable
fun InquiryItem(
    inquiries: List<Inquiry>,
    it: Int,
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .padding(16.dp)
            .clickable {
                navController.navigate(Route.InquiryDetail.createRouteWithId(inquiries[it].id!!))
            }
    ) {
        Row {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = inquiries[it].title!!,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(text = inquiries[it].message!!)
            }
            Gap.k16.Width()
            if (inquiries[it].inquiryResponse != null) {
                Icon(
                    painter = painterResource(id = R.drawable.round_mark_email_read_24),
                    tint = Color(0xFF228B22),
                    contentDescription = "Received Reply"
                )
            }
        }
    }
}