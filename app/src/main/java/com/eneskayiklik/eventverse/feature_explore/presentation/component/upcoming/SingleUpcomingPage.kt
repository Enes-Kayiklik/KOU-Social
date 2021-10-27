package com.eneskayiklik.eventverse.feature_explore.presentation.component.upcoming

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.eneskayiklik.eventverse.feature_explore.domain.model.ExploreEventModel

@ExperimentalMaterialApi
@Composable
fun SingleUpcomingPage(
    data: ExploreEventModel,
    onItemClick: (id: String) -> Unit
) {
    Surface(
        modifier = Modifier
            .width(230.dp),
        elevation = 2.dp,
        onClick = {
            onItemClick(data.title)
        }
    ) {
        Column(
            modifier = Modifier.padding(0.dp)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                painter = rememberImagePainter(data = data.imgUrl, builder = {
                    crossfade(true)
                }),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = data.date,
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.primary
                )
                Text(
                    text = data.title,
                    style = MaterialTheme.typography.h1.copy(fontSize = 18.sp),
                    maxLines = 3
                )
                /*Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = "")
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = data.location, style = MaterialTheme.typography.h2)
                }*/
            }
        }
    }
}