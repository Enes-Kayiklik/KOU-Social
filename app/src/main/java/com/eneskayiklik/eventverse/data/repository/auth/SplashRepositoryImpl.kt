package com.eneskayiklik.eventverse.data.repository.auth

import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.util.Screen
import com.eneskayiklik.eventverse.util.Settings
import com.eneskayiklik.eventverse.data.event.auth.SplashEvent
import com.eneskayiklik.eventverse.data.model.auth.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class SplashRepositoryImpl(
    private val auth: FirebaseAuth = Firebase.auth,
    private val db: FirebaseFirestore = Firebase.firestore,
) {

    suspend fun checkUser(): SplashEvent {
        return try {
            if (auth.currentUser == null) SplashEvent.OnNavigate(Screen.Intro.route)
            else {
                auth.currentUser?.reload()?.await()
                if (auth.currentUser?.isEmailVerified == false) {
                    SplashEvent.ShowVerifyPopup
                } else {
                    getUser()
                    SplashEvent.OnNavigate(Screen.Home.route)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            SplashEvent.OnNavigate(Screen.Intro.route)
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

    suspend fun resendMail(): Boolean {
        return try {
            auth.currentUser?.sendEmailVerification()?.await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
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
}