package com.eneskayiklik.eventverse.feature.events.detail.lazy_items.tab.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.data.model.event_detail.Comment
import com.eneskayiklik.eventverse.feature.profile.profile.component.ProfileImage
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize

@Composable
fun SingleCommentItem(
    comment: Comment
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .border(
                1.5.dp,
                MaterialTheme.colors.secondary,
                RoundedCornerShape(10.dp)
            )
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            ProfileImage(picUrl = comment.user.profilePic, modifier = Modifier.size(32.dp))
            Column {
                Text(
                    text = comment.user.fullName, style = MaterialTheme.typography.h1.copy(
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 14.sp
                    )
                )
                RatingBar(
                    value = comment.rating,
                    config = RatingBarConfig()
                        .stepSize(StepSize.HALF)
                        .numStars(5)
                        .isIndicator(true)
                        .size(16.dp)
                        .style(RatingBarStyle.HighLighted),
                    onValueChange = { },
                    onRatingChanged = { }
                )
            }
            Spacer(modifier = Modifier.weight(1F))
            Text(
                text = comment.formattedDate, style = MaterialTheme.typography.h1.copy(
                    color = MaterialTheme.colors.onSecondary,
                    fontSize = 12.sp
                )
            )
        }
        if (comment.shouldShowComment)
            Text(
                text = comment.comment, style = MaterialTheme.typography.h4.copy(
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 16.sp
                )
            )
    }
}