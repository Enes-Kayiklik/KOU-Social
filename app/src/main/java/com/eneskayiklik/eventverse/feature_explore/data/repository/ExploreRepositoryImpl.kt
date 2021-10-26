package com.eneskayiklik.eventverse.feature_explore.data.repository

import com.eneskayiklik.eventverse.feature_explore.domain.model.UpcomingEventModel
import com.eneskayiklik.eventverse.feature_explore.domain.repository.ExploreRepository

class ExploreRepositoryImpl : ExploreRepository {
    override suspend fun getUpcomingEvents(): List<UpcomingEventModel> {
        return listOf(
            UpcomingEventModel(
                imgUrl = "https://io.google/2021/assets/io_social_asset.jpg",
                title = "Google IO",
                location = "Google/Online",
                date = "May 18-20"
            ),
            UpcomingEventModel(
                imgUrl = "https://www.apple.com/v/apple-events/home/t/images/meta/overview__bcphzsdb4fpu_og.png?202110171420",
                title = "Apple Event",
                location = "Apple/Online",
                date = "Jun 10-13"
            ),
            UpcomingEventModel(
                imgUrl = "https://www.creatopy.com/blog/wp-content/uploads/2019/06/facebook-event-cover-image-template-4.jpg",
                title = "Cocktail Party",
                location = "My House",
                date = "Tomorrow 10 PM"
            ),
        )
    }
}