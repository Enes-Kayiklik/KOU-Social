package com.eneskayiklik.eventverse.feature_auth.data.repository

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

    suspend fun getUser() {
        /* return try {
            auth.currentUser?.reload()?.await()
            if (auth.currentUser != null) {

            }
        } catch (e: Exception) {

        } */
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
}