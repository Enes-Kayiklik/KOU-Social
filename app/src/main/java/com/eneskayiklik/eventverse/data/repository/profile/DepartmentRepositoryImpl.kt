package com.eneskayiklik.eventverse.data.repository.profile

import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.data.model.auth.Faculty
import com.eneskayiklik.eventverse.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class DepartmentRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore,
) {

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
}