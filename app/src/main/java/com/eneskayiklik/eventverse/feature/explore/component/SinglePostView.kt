package com.eneskayiklik.eventverse.feature.explore.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.eneskayiklik.eventverse.data.model.share.Post
import com.eneskayiklik.eventverse.feature.polls.component.poll_view.UserSection
import com.eneskayiklik.eventverse.R

@Composable
fun SinglePostView(
    post: Post,
    modifier: Modifier = Modifier,
    onPostAction: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp), verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        UserSection(
            post.fromUser,
            post.formattedDate
        ) {

        }
        PostBody(body = post.body, image = post.image)
        PostInteractions(isLiked = post.isUserLike, likeCount = post.likeCount)
    }
}

@Composable
private fun PostBody(
    body: String,
    image: String
) {
    Text(
        text = body, style = MaterialTheme.typography.h1.copy(
            color = MaterialTheme.colors.onBackground,
            fontSize = 16.sp
        ), maxLines = 3, overflow = TextOverflow.Ellipsis
    )

    if (image.isNotEmpty()) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            loading = {

            },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
    }
}

@Composable
private fun PostInteractions(
    isLiked: Boolean,
    likeCount: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(painter = painterResource(id = R.drawable.ic_like), contentDescription = null)
        }
        Text(
            text = likeCount.toString(), style = MaterialTheme.typography.h1.copy(
                color = MaterialTheme.colors.onSecondary,
                fontSize = 12.sp
            )
        )
        IconButton(onClick = { /*TODO*/ }) {
            Icon(painter = painterResource(id = R.drawable.ic_reviews), contentDescription = null)
        }
    }
}