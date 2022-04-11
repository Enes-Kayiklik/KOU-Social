package com.eneskayiklik.eventverse.feature_announcement.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.eventverse.core.util.Settings
import com.eneskayiklik.eventverse.feature_announcement.data.model.Announcement
import com.eneskayiklik.eventverse.feature_announcement.data.state.AnnouncementState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import javax.inject.Inject

@HiltViewModel
class AnnouncementViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(AnnouncementState())
    val state: StateFlow<AnnouncementState> = _state

    init {
        getAnnouncements()
    }

    private fun getAnnouncements() {
        viewModelScope.launch(Dispatchers.IO) {
            // Get announcement source url from user data
            val departmentUrl = Settings.currentUser.department.departmentUrl
            val baseUrl = Settings.currentUser.department.baseUrl
            if (departmentUrl.isEmpty()) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    isRefreshing = false,
                    showEmptyDepartmentView = true
                )
                return@launch
            }
            // Connect that url using Jsoup see: https://jsoup.org/
            val doc = Jsoup.connect(departmentUrl).get()
            // Parse that HTML file
            // Note: This works only for 'https://kocaeli.edu.tr/duyurular.php' links
            val elements = doc.select("div.panel-body").first()
                ?.select("div.row")?.first()?.select("div.col-md-6")
            val announcementList = mutableListOf<Announcement>()
            elements?.forEach { element ->
                val body = element.select("dl.dl-horizontal").first()?.select("dd")
                announcementList.add(
                    Announcement(
                        title = element.select("h4").text(),
                        date = element.select("p > span").first()?.text() ?: "",
                        sender = element.select("p > span").last()?.text() ?: "",
                        activeDateRange = body?.getOrNull(0)?.text() ?: "",
                        content = body?.getOrNull(1)?.text() ?: "",
                        attachment = body?.getOrNull(2)?.select("a")?.first()?.attr("href")
                            ?: "",
                        baseUrl = baseUrl
                    )
                )
            }
            _state.value = _state.value.copy(
                announcements = announcementList,
                isLoading = false,
                isRefreshing = false,
                showEmptyDepartmentView = false
            )
        }
    }

    fun refreshAnnouncements() {
        viewModelScope.launch {
            if (_state.value.isLoading.not() && _state.value.isRefreshing.not()) {
                _state.value = _state.value.copy(isRefreshing = true)
                getAnnouncements()
            }
        }
    }

    fun showAnnouncementPopup(item: Announcement) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isPopupActive = true,
                activeAnnouncement = item
            )
        }
    }

    fun closePopup() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isPopupActive = false,
                activeAnnouncement = Announcement()
            )
        }
    }
}