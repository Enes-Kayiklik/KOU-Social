package com.eneskayiklik.eventverse.feature.explore.component

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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
import com.eneskayiklik.eventverse.core.ui.theme.Red
import com.eneskayiklik.eventverse.util.Screen

@Composable
fun SinglePostView(
    post: Post,
    modifier: Modifier = Modifier,
    onLike: () -> Unit,
    onNavigate: (String) -> Unit
) {
    Column(
        modifier = modifier, verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        UserSection(
            post.fromUser,
            post.formattedDate,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            onNavigate(Screen.Profile.route(userId = post.fromUser.userId))
        }
        PostBody(
            body = post.body,
            image = post.image,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 5.dp)
        )
        //PostInteractions(isLiked = post.isUserLike, likeCount = post.likeCount, onLike = onLike)
    }
}

@Composable
private fun PostBody(
    body: String,
    image: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(5.dp)) {
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
        animateColorAsState(targetValue = if (isLiked) Red else MaterialTheme.colors.onBackground).value
    val scaleAnim = animateFloatAsState(
        targetValue = if (isLiked) 1.2F else 1F,
        animationSpec = spring(Spring.DampingRatioHighBouncy, stiffness = 900F)
    ).value

    Row(
        modifier = Modifier
            .animateContentSize()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(
            painter = painterResource(id = if (isLiked) R.drawable.ic_like_active else R.drawable.ic_like),
            contentDescription = null,
            tint = colorAnim,
            modifier = Modifier
                .padding(start = 16.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false),
                    onClick = onLike
                )
                .scale(scaleAnim)
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
        Icon(
            painter = painterResource(id = R.drawable.ic_reviews),
            contentDescription = null,
            tint = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false),
                    onClick = onLike
                )
        )
    }
}