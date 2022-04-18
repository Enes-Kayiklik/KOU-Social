package com.eneskayiklik.eventverse.feature.profile.profile.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.R

@Composable
fun LogoutButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(modifier = Modifier, contentAlignment = Alignment.Center) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(5.dp))
                .clickable { onClick() }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_logout),
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = stringResource(id = R.string.logout),
                style = MaterialTheme.typography.h1.copy(color = MaterialTheme.colors.onBackground, fontSize = 16.sp)
            )
        }
    }
}