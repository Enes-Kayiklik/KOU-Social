package com.eneskayiklik.eventverse.data.repository.create

import android.content.Context
import android.net.Uri
import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.data.model.create.CreateEventModel
import com.eneskayiklik.eventverse.util.Settings
import com.eneskayiklik.eventverse.util.extension.fileFromContentUri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import id.zelory.compressor.Compressor
import kotlinx.coroutines.tasks.await

class CreateEventRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore,
    private val storage: FirebaseStorage = Firebase.storage,
    private val context: Context
) {

    suspend fun createEvent(event: CreateEventModel): CreateResult {
        val userId = Settings.currentUser.userId
        val picPath =
            if (event.coverImage.isNotEmpty()) uploadEventImage(event.coverImage, userId) else null
        val document = event.copy(
            coverImage = picPath ?: ""
        )
        return try {
            db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("events")
                .collection("events")
                .add(document)
                .await()
            CreateResult(isSuccess = true)
        } catch (e: Exception) {
            e.printStackTrace()
            CreateResult(e.message ?: "")
        }
    }

    private suspend fun uploadEventImage(uri: String, userId: String): String? {
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