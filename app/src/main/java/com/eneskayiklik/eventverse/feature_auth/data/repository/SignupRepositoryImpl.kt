package com.eneskayiklik.eventverse.feature_auth.data.repository

import com.eneskayiklik.eventverse.feature_auth.domain.LoginResult
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.core.util.Resource
import com.eneskayiklik.eventverse.core.util.Settings
import com.eneskayiklik.eventverse.feature_auth.data.model.User
import com.eneskayiklik.eventverse.feature_auth.domain.model.Faculty
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class SignupRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore,
    private val auth: FirebaseAuth = Firebase.auth
) {
    suspend fun signupWithEmail(email: String, password: String, fullName: String): LoginResult {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            saveUserToDb(email, fullName, result.user?.uid)
            result.user?.sendEmailVerification()?.await()
            LoginResult(isSuccess = true)
        } catch (e: FirebaseAuthUserCollisionException) {
            LoginResult(emailError = "Email address is already in use", isSuccess = false)
        } catch (e: Exception) {
            e.printStackTrace()
            LoginResult(isSuccess = false)
        }
    }

    private suspend fun saveUserToDb(email: String, fullName: String, uid: String?) {
        if (uid.isNullOrEmpty()) throw FirebaseAuthInvalidCredentialsException("", "")
        val user = User(
            email = email,
            fullName = fullName,
            userId = uid
        )
        Settings.currentUser = user
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

    suspend fun checkEmailVerified(): Boolean {
        return try {
            auth.currentUser?.reload()?.await()
            auth.currentUser?.isEmailVerified == true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun getFaculties() = flow {
        emit(Resource.Loading())
        val data = try {
            db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("faculties")
                .collection("faculties")
                .get()
                .await()
                .toObjects(Faculty::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        if (data != null) emit(Resource.Success(data)) else emit(Resource.Error(""))
    }

    suspend fun resendMail(): Boolean {
        return try {
            auth.currentUser?.sendEmailVerification()?.await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}