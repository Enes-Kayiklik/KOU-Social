package com.eneskayiklik.eventverse.feature.profile.profile.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.data.model.auth.SocialAccount
import com.eneskayiklik.eventverse.util.extension.toGithubLink
import com.eneskayiklik.eventverse.util.extension.toInstagramLink
import com.eneskayiklik.eventverse.util.extension.toLinkedInLink
import com.eneskayiklik.eventverse.util.extension.toTwitterLink

@Composable
fun UserSocialSection(
    socialAccounts: SocialAccount
) {
    val uriHandler = LocalUriHandler.current
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        if (socialAccounts.github.isNotEmpty())
            Image(
                painter = painterResource(id = R.drawable.ic_github),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .shadow(5.dp, CircleShape)
                    .clickable { uriHandler.openUri(socialAccounts.github.toGithubLink()) }
            )

        if (socialAccounts.linkedIn.isNotEmpty())
            Image(
                painter = painterResource(id = R.drawable.ic_linkedin),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .shadow(5.dp, CircleShape)
                    .clickable { uriHandler.openUri(socialAccounts.linkedIn.toLinkedInLink()) }
            )

        if (socialAccounts.instagram.isNotEmpty())
            Image(
                painter = painterResource(id = R.drawable.ic_instagram),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .shadow(5.dp, CircleShape)
                    .clickable { uriHandler.openUri(socialAccounts.instagram.toInstagramLink()) }
            )

        if (socialAccounts.twitter.isNotEmpty())
            Image(
                painter = painterResource(id = R.drawable.ic_twitter),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .shadow(5.dp, CircleShape)
                    .clickable { uriHandler.openUri(socialAccounts.twitter.toTwitterLink()) }
            )
    }
}