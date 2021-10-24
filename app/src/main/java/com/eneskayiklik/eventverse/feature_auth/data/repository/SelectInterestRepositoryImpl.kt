package com.eneskayiklik.eventverse.feature_auth.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import com.eneskayiklik.eventverse.feature_auth.domain.model.InterestModel
import com.eneskayiklik.eventverse.feature_auth.domain.repository.SelectInterestRepository

class SelectInterestRepositoryImpl : SelectInterestRepository {
    override suspend fun getInterests(): List<InterestModel> {
        return listOf(
            InterestModel(
                0, "Sports", Icons.Rounded.Sports, false
            ),
            InterestModel(
                1, "Games", Icons.Rounded.Games, false
            ),
            InterestModel(
                2, "Music", Icons.Rounded.MusicNote, false
            ),
            InterestModel(
                3, "Museum", Icons.Rounded.Museum, false
            ),
            InterestModel(
                4, "Development", Icons.Rounded.DeveloperMode, false
            ),
            InterestModel(
                5, "Travel", Icons.Rounded.AirplaneTicket, false
            ),
            InterestModel(
                6, "Movie", Icons.Rounded.Movie, false
            ),
            InterestModel(
                7, "Banking", Icons.Rounded.Atm, false
            ),
            InterestModel(
                8, "Art", Icons.Rounded.Brush, false
            ),
        )
    }
}