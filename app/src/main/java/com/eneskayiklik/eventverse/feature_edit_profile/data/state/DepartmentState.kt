package com.eneskayiklik.eventverse.feature_edit_profile.data.state

import com.eneskayiklik.eventverse.feature_auth.domain.model.Faculty

data class DepartmentState(
    val faculty: List<Faculty> = emptyList()
) {
    val isLoading = faculty.isEmpty()
}
