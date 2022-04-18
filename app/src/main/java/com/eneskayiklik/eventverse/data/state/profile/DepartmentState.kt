package com.eneskayiklik.eventverse.data.state.profile

import com.eneskayiklik.eventverse.data.model.auth.Faculty

data class DepartmentState(
    val faculty: List<Faculty> = emptyList()
) {
    val isLoading = faculty.isEmpty()
}
