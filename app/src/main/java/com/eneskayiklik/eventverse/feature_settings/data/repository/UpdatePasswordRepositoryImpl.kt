package com.eneskayiklik.eventverse.feature_settings.data.repository

import com.eneskayiklik.eventverse.core.util.Resource
import com.eneskayiklik.eventverse.feature_settings.data.model.UpdatePasswordResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class UpdatePasswordRepositoryImpl(
    private val auth: FirebaseAuth = Firebase.auth
) {
    suspend fun resetUserPassword(
        email: String,
        oldPassword: String,
        newPassword: String
    ) = flow {
        emit(Resource.Loading())
        val user = auth.currentUser
        if (user == null) emit(
            Resource.Error(
                "", data = UpdatePasswordResult(
                    isSuccess = false, otherError = "Unknown Error"
                )
            )
        ) else {
            try {
                val credential = EmailAuthProvider.getCredential(email, oldPassword)
                user.reauthenticate(credential).await()
            } catch (e: FirebaseAuthInvalidUserException) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        "", data = UpdatePasswordResult(
                            isSuccess = false, emailError = "The email is invalid"
                        )
                    )
                )
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        "", data = UpdatePasswordResult(
                            isSuccess = false, otherError = "The password is invalid"
                        )
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        "", data = UpdatePasswordResult(
                            isSuccess = false, otherError = "Unknown Error"
                        )
                    )
                )
            }
            user.updatePassword(newPassword).await()
            emit(Resource.Success(UpdatePasswordResult()))
        }
    }
}