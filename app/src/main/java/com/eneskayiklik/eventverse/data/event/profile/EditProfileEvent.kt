package com.eneskayiklik.eventverse.data.event.profile

import android.net.Uri
import com.eneskayiklik.eventverse.data.model.auth.Department
import java.time.LocalDate

sealed class EditProfileEvent {
    data class OnFullName(val text: String) : EditProfileEvent()
    data class OnPhoto(val uri: Uri) : EditProfileEvent()
    data class OnCropper(val uri: Uri) : EditProfileEvent()
    data class OnInstagram(val text: String) : EditProfileEvent()
    data class OnLinkedIn(val text: String) : EditProfileEvent()
    data class OnTwitter(val text: String) : EditProfileEvent()
    data class OnGitHub(val text: String) : EditProfileEvent()
    data class OnDepartment(val department: Department) : EditProfileEvent()
    data class ShowDepartmentPopup(val isVisible: Boolean) : EditProfileEvent()
    data class OnBirthdate(val date: LocalDate) : EditProfileEvent()
}
