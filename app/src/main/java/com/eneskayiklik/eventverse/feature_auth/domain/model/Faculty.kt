package com.eneskayiklik.eventverse.feature_auth.domain.model

data class Faculty(
    val departments: List<Department> = emptyList(),
    val name: String = ""
)

data class Department(
    val departmentName: String = "",
    val departmentUrl: String = ""
)
