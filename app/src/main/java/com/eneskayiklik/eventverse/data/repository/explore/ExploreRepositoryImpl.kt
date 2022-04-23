package com.eneskayiklik.eventverse.data.repository.explore

import android.util.Log
import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.data.model.share.PostDto
import com.eneskayiklik.eventverse.util.Resource
import com.eneskayiklik.eventverse.util.Settings
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ExploreRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore
) {

    private var lastSnapshot: DocumentSnapshot? = null
    suspend fun getPosts(getNext: Boolean, isRefreshing: Boolean = false) = flow {
        if (getNext.not()) return@flow
        // Refresh page
        if (isRefreshing) lastSnapshot = null
        try {
            Log.e("TAG", "getPosts: $lastSnapshot", )
            emit(Resource.Loading())
            if (Settings.userStorage.isEmpty()) Settings.getAllUsers(db)
            val query = db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("posts")
                .collection("posts")
                .orderBy("createdAt", Query.Direction.DESCENDING)
            val snapshot = if (lastSnapshot != null)
                query.startAfter(lastSnapshot)
                    .limit(4)
                    .get().await()
            else query.limit(4).get().await()
            lastSnapshot = snapshot.documents.last()
            val data = snapshot.toObjects(PostDto::class.java)
                .map { it.toPost() }
            emit(Resource.Success(data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: ""))
            Log.e("TAG", "getPosts: $e")
        }
    }

    suspend fun likePost(
        postId: String,
        like: Boolean
    ) {
        val userId = Settings.currentUser.userId
        try {
            db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("posts")
                .collection("posts")
                .document(postId)
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