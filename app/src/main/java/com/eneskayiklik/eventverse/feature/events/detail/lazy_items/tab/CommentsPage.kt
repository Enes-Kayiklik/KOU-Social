package com.eneskayiklik.eventverse.feature.events.detail.lazy_items.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.data.event.auth.event_detail.EventDetailEvent
import com.eneskayiklik.eventverse.data.model.event_detail.Comment
import com.eneskayiklik.eventverse.feature.auth.login.component.LoginButton
import com.eneskayiklik.eventverse.feature.events.detail.lazy_items.tab.component.SingleCommentItem
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize

@Composable
fun CommentsPage(
    comment: Comment,
    comments: List<Comment>,
    onEvent: (EventDetailEvent) -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.comment_title),
            style = MaterialTheme.typography.h1.copy(
                color = MaterialTheme.colors.onBackground,
                fontSize = 24.sp
            ), modifier = Modifier.padding(horizontal = 24.dp)
        )
        RatingBar(
            value = comment.rating,
            config = RatingBarConfig()
                .stepSize(StepSize.HALF)
                .numStars(5)
                .style(RatingBarStyle.HighLighted),
            onValueChange = {
                onEvent(EventDetailEvent.OnRating(it))
            },
            onRatingChanged = { }
        )
        Text(
            text = stringResource(id = R.string.comment_subtitle),
            style = MaterialTheme.typography.h1.copy(
                color = MaterialTheme.colors.onBackground,
                fontSize = 24.sp
            ), textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .shadow(3.dp, RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
        ) {
            BasicTextField(
                value = comment.comment,
                onValueChange = {
                    onEvent(EventDetailEvent.OnComment(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                textStyle = MaterialTheme.typography.h1.copy(
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 16.sp
                ), cursorBrush = SolidColor(MaterialTheme.colors.primary)
            )
            if (comment.comment.isEmpty())
                Text(
                    text = stringResource(id = R.string.share_write_desc),
                    style = MaterialTheme.typography.h1.copy(
                        color = MaterialTheme.colors.onSecondary,
                        fontSize = 16.sp
                    )
                )
        }
        LoginButton(
            text = stringResource(id = R.string.share_review),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            onEvent(EventDetailEvent.OnReview)
        }

        comments.forEach {
            SingleCommentItem(comment = it)
        }
    }
}