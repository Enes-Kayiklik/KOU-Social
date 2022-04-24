package com.eneskayiklik.eventverse.feature.explore.component

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    onLike: () -> Unit,
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
        PostInteractions(isLiked = post.isUserLike, likeCount = post.likeCount, onLike = onLike)
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
                .clip(RoundedCornerShape(10.dp))
        )
    }
}

@OptIn(
    ExperimentalAnimationApi::class
)
@Composable
private fun PostInteractions(
    isLiked: Boolean,
    likeCount: Int,
    onLike: () -> Unit
) {
    val colorAnim =
        animateColorAsState(targetValue = if (isLiked.not()) MaterialTheme.colors.onBackground else MaterialTheme.colors.error).value

    Row(
        modifier = Modifier
            .animateContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_like),
            contentDescription = null,
            tint = colorAnim,
            modifier = Modifier.clickable {
                onLike()
            }
        )
        AnimatedContent(
            targetState = likeCount,
            transitionSpec = {
                if (targetState > initialState) {
                    slideInVertically { height -> height } + fadeIn() with
                            slideOutVertically { height -> -height } + fadeOut()
                } else {
                    slideInVertically { height -> -height } + fadeIn() with
                            slideOutVertically { height -> height } + fadeOut()
                }.using(
                    SizeTransform(clip = false)
                )
            }
        ) { targetCount ->
            if (targetCount != 0)
                Text(
                    text = "$targetCount",
                    style = MaterialTheme.typography.h4.copy(color = colorAnim, fontSize = 12.sp)
                )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(painter = painterResource(id = R.drawable.ic_reviews), contentDescription = null)
        }
    }
}