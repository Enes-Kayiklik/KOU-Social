package com.eneskayiklik.eventverse.feature_auth.presentation.interest.component

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eneskayiklik.eventverse.core.ui.theme.White
import com.eneskayiklik.eventverse.feature_auth.domain.model.InterestModel

@ExperimentalMaterialApi
@Composable
fun SingleInterestModel(
    data: InterestModel,
    onItemClick: (id: Int) -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue =
        if (data.isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    )
    val iconTint by animateColorAsState(
        targetValue =
        if (data.isSelected) White else MaterialTheme.colors.onSurface
    )

    Surface(
        modifier = Modifier
            .padding(6.dp),
        onClick = { onItemClick(data.id) },
        shape = RoundedCornerShape(8.dp),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = data.icon,
                contentDescription = data.title,
                modifier = Modifier.size(40.dp),
                tint = iconTint
            )
            Text(text = data.title, style = MaterialTheme.typography.h2, color = iconTint)
        }
    }
}

@ExperimentalMaterialApi
@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
fun PrevInterest() {
    SingleInterestModel(data = InterestModel(
        2, "Music", Icons.Rounded.MusicNote, false
    ), onItemClick = { })
}