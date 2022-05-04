package com.eneskayiklik.eventverse.data.repository.profile

import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.data.model.share.PostDto
import com.eneskayiklik.eventverse.util.POST_PAGE_SIZE
import com.eneskayiklik.eventverse.util.Resource
import com.eneskayiklik.eventverse.util.Settings
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ProfilePostsRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore
) {

    //private var lastTimestamp: Timestamp? = null
    suspend fun getPosts(getNext: Boolean, userId: String) = flow {
        if (getNext.not()) return@flow
        try {
            emit(Resource.Loading())
            if (Settings.userStorage.isEmpty()) Settings.getAllUsers(db)
            val query = db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("posts")
                .collection("posts")
                .whereEqualTo("userId", userId)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                //.limit(POST_PAGE_SIZE)
                // We can't combine two different compare function see: https://firebase.google.com/docs/firestore/query-data/queries#kotlin+ktx_3
                //.whereLessThan("createdAt", lastTimestamp ?: Timestamp.now())
                .get()
                .await()
            val data = query.toObjects(PostDto::class.java)
                .map { it.toPost() }
            //lastTimestamp = data.last().createdAt
            emit(Resource.Success(data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: ""))
        }
    }
}