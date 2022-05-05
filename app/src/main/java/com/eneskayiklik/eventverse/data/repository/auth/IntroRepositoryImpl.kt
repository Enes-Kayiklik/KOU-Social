package com.eneskayiklik.eventverse.data.repository.auth

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.util.Resource
import com.eneskayiklik.eventverse.util.Settings
import com.eneskayiklik.eventverse.data.model.auth.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class IntroRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore,
    private val auth: FirebaseAuth = Firebase.auth
) {
    suspend fun signupWithGoogle(account: GoogleSignInAccount) = flow {
        try {
            emit(Resource.Loading())
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)

            val result = auth.signInWithCredential(credential).await()
            if (result.additionalUserInfo?.isNewUser == true)
                saveUserToDb(
                    result.user?.email ?: "",
                    result.user?.displayName ?: "",
                    result.user?.uid
                )
            else getUser()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: ""))
        }
    }

    private suspend fun getUser() {
        try {
            val user = db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("users")
                .collection("users")
                .document("${auth.currentUser?.uid}")
                .get()
                .await()
                .toObject(User::class.java)
            Settings.currentUser = user?.toAppUser() ?: return
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun saveUserToDb(email: String, fullName: String, uid: String?) {
        if (uid.isNullOrEmpty()) throw FirebaseAuthInvalidCredentialsException("", "")
        val user = User(
            email = email,
            fullName = fullName,
            userId = uid,
            socialLogin = true
        )
        Settings.currentUser = user.toAppUser()
        try {
            db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("users")
                .collection("users")
                .document("$uid")
                .set(user)
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}