package com.eneskayiklik.eventverse.feature.polls.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eneskayiklik.eventverse.R

@Composable
fun PollsToolbar(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.polls),
            style = MaterialTheme.typography.h3.copy(
                color = MaterialTheme.colors.onBackground,
                fontSize = 20.sp
            ), modifier = Modifier.align(Alignment.Center)
        )

        Divider(
            modifier = Modifier
                .height(1.dp)
                .background(MaterialTheme.colors.secondary)
                .align(Alignment.BottomCenter)
        )
    }
}