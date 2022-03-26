package com.eneskayiklik.eventverse.feature_auth.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.core.util.Screen
import com.eneskayiklik.eventverse.core.util.Settings
import com.eneskayiklik.eventverse.feature_auth.data.event.LoginEvent
import com.eneskayiklik.eventverse.feature_auth.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class LoginRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore,
    private val auth: FirebaseAuth = Firebase.auth
) {
    suspend fun loginWithEmail(email: String, password: String): LoginEvent {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            if (result.user?.isEmailVerified == false) {
                LoginEvent.ShowVerifyPopup
            } else {
                getUser()
                LoginEvent.OnNavigate(Screen.Explore.route)
            }
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            LoginEvent.PasswordError
        } catch (e: FirebaseAuthInvalidUserException) {
            LoginEvent.UserError
        } catch (e: Exception) {
            e.printStackTrace()
            LoginEvent.ShowErrorPopup
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

    suspend fun checkEmailVerified(): Boolean {
        return try {
            auth.currentUser?.reload()?.await()
            if (auth.currentUser?.isEmailVerified == true) {
                getUser()
                true
            } else false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
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