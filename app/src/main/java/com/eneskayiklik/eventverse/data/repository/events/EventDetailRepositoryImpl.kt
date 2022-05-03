package com.eneskayiklik.eventverse.data.repository.events

import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.data.model.create.EventDto
import com.eneskayiklik.eventverse.util.Resource
import com.eneskayiklik.eventverse.util.Settings
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class EventDetailRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore
) {

    suspend fun getEventDetail(eventId: String) = flow {
        try {
            emit(Resource.Loading())
            if (Settings.userStorage.isEmpty()) Settings.getAllUsers(db)
            val data = db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("events")
                .collection("events")
                .document(eventId)
                .get().await().toObject(EventDto::class.java)?.toEvent()
            emit(Resource.Success(data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: ""))
        }
    }

    suspend fun attendeeEvent(
        eventId: String
    ) {
        try {
            db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("events")
                .collection("events")
                .document(eventId)
                .update(
                    "attendee",
                    FieldValue.arrayUnion(Settings.currentUser.userId)
                ).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun likeEvent(
        eventId: String,
        like: Boolean
    ) {
        val userId = Settings.currentUser.userId
        try {
            db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("events")
                .collection("events")
                .document(eventId)
                .update(
                    "likedBy",
                    if (like) FieldValue.arrayUnion(userId)
                    else FieldValue.arrayRemove(userId)
                ).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}