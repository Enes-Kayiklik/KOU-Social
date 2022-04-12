package com.eneskayiklik.eventverse.feature_settings.data.state

import com.eneskayiklik.eventverse.feature_auth.domain.model.Faculty

data class DepartmentState(
    val faculty: List<Faculty> = emptyList()
) {
    val isLoading = faculty.isEmpty()
}
