package com.eneskayiklik.eventverse.feature.meal.component.meal_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.data.model.meal.Dish

@Composable
fun SingleDishItem(
    dish: Dish
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MealImage(picUrl = dish.image, modifier = Modifier.size(90.dp))
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(
                text = dish.name, style = MaterialTheme.typography.h1.copy(
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 18.sp
                )
            )
            Text(
                text = stringResource(id = R.string.dish_calory, dish.calorie),
                style = MaterialTheme.typography.h1.copy(
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 14.sp
                )
            )
        }
    }
}

@Composable
private fun MealImage(
    picUrl: String,
    modifier: Modifier = Modifier
) {
    if (picUrl.isNotEmpty())
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(picUrl)
                .crossfade(true)
                .build(),
            loading = {
                EmptyImageBox(modifier)
            },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .clip(RoundedCornerShape(15.dp))
        ) else EmptyImageBox(modifier)
}

@Composable
private fun EmptyImageBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(
            MaterialTheme.colors.secondary,
            RoundedCornerShape(15.dp)
        ), contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baking),
            contentDescription = null,
            tint = MaterialTheme.colors.onSecondary,
            modifier = Modifier.fillMaxSize(.5F)
        )
    }
}