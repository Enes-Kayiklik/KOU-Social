package com.eneskayiklik.eventverse.feature_share.data.repository

import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.core.util.Settings
import com.eneskayiklik.eventverse.feature_share.data.model.Post
import com.eneskayiklik.eventverse.feature_share.data.model.ShareResult
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ShareRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore
) {

    suspend fun sharePost(title: String, body: String): ShareResult {
        val post = Post(
            title = title,
            body = body,
            fromUser = Settings.currentUser.toPostUser()
        )

        return try {
            db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("posts")
                .collection("posts")
                .add(post)
                .await()
            ShareResult(isSuccess = true)
        } catch (e: Exception) {
            e.printStackTrace()
            ShareResult(error = e.message ?: "")
        }
    }
}