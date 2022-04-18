package com.eneskayiklik.eventverse.data.repository.share

import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.util.Settings
import com.eneskayiklik.eventverse.data.model.share.Post
import com.eneskayiklik.eventverse.data.model.share.ShareResult
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