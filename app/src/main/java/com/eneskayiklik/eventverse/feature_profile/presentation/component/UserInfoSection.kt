package com.eneskayiklik.eventverse.feature_profile.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.feature_auth.domain.model.Department

@Composable
fun UserInfoSection(
    photoUrl: String,
    name: String,
    department: Department
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        ProfileImage(picUrl = photoUrl, modifier = Modifier.size(120.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.h1.copy(
                MaterialTheme.colors.onBackground,
                fontSize = 24.sp
            )
        )
        if (department.departmentName.isNotEmpty())
            Text(
                text = department.departmentName,
                style = MaterialTheme.typography.h2.copy(
                    MaterialTheme.colors.onSurface,
                    fontSize = 14.sp
                )
            )
    }
}