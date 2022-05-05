package com.eneskayiklik.eventverse.util

import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.data.model.auth.AppUser
import com.eneskayiklik.eventverse.data.model.auth.PostUser
import com.eneskayiklik.eventverse.data.model.auth.User
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


object Settings {
    var currentUser: AppUser = AppUser()
        set(value) {
            Firebase.crashlytics.setUserId(field.userId)
            field = value
        }
    var userStorage: List<PostUser> = emptyList()
    suspend fun getAllUsers(db: FirebaseFirestore) {
        try {
            userStorage = db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("users")
                .collection("users")
                .get().await()
                .toObjects(User::class.java)
                .map { it.toPostUser() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}