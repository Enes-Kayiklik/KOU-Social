package com.eneskayiklik.eventverse.feature_settings.data.repository

import android.content.Context
import android.net.Uri
import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.core.util.Resource
import com.eneskayiklik.eventverse.core.util.Settings
import com.eneskayiklik.eventverse.core.util.extension.fileFromContentUri
import com.eneskayiklik.eventverse.feature_auth.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import id.zelory.compressor.Compressor
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class EditProfileRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore,
    private val storage: FirebaseStorage = Firebase.storage,
    private val context: Context
) {

    suspend fun updateUser(user: User, isProfilePicUpdated: Boolean) = flow {
        emit(Resource.Loading())
        val currentUser = Settings.currentUser
        val picPath =
            if (isProfilePicUpdated) uploadProfilePic(user.profilePic, currentUser.userId) else null
        val newUserData = user.copy(profilePic = picPath ?: currentUser.profilePic)

        Settings.currentUser = user.toAppUser()
        try {
            db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("users")
                .collection("users")
                .document(currentUser.userId)
                .set(newUserData)
                .await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: ""))
        }
    }

    private suspend fun uploadProfilePic(uri: String, userId: String): String? {
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