package com.eneskayiklik.eventverse.data.repository.share

import android.content.Context
import android.net.Uri
import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.util.Settings
import com.eneskayiklik.eventverse.data.model.share.PostDto
import com.eneskayiklik.eventverse.util.Resource
import com.eneskayiklik.eventverse.util.extension.fileFromContentUri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import id.zelory.compressor.Compressor
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ShareRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore,
    private val storage: FirebaseStorage = Firebase.storage,
    private val context: Context
) {

    suspend fun sharePost(body: String, image: String) = flow {
        emit(Resource.Loading())
        val userId = Settings.currentUser.userId
        val picPath =
            if (image.isNotEmpty()) uploadPostImage(image, userId) else null
        val post = PostDto(
            body = body,
            userId = userId,
            image = picPath ?: ""
        )

        try {
            db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("posts")
                .collection("posts")
                .add(post)
                .await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: ""))
        }
    }

    private suspend fun uploadPostImage(uri: String, userId: String): String? {
        return try {
            val compressedImageFile =
                Compressor.compress(context, Uri.parse(uri).fileFromContentUri(context))
            val file = Uri.fromFile(compressedImageFile)
            val storageRef = storage.reference
            val userPhotoRef = storageRef.child("images/$userId/${compressedImageFile.name}")
            userPhotoRef.putFile(file).await()
            userPhotoRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}