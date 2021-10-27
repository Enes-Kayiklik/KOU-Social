package com.eneskayiklik.eventverse.feature_explore.presentation.component.upcoming

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.eneskayiklik.eventverse.feature_explore.domain.model.UpcomingEventModel
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun SingleUpcomingPage(
    data: UpcomingEventModel,
    onItemClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .width(240.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 2.dp,
        onClick = onItemClick
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                painter = rememberImagePainter(data = data.imgUrl, builder = {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(15F))
                }),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = data.title, maxLines = 1)
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = "")
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = data.location, style = MaterialTheme.typography.h2)
            }
        }
    }
}