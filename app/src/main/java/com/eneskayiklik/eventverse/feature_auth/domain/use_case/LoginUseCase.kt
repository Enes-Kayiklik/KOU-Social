package com.eneskayiklik.eventverse.feature_auth.domain.use_case

import android.content.Context
import com.eneskayiklik.eventverse.R
import com.eneskayiklik.eventverse.core.util.extension.isValidEmail
import com.eneskayiklik.eventverse.core.util.extension.isValidPassword
import com.eneskayiklik.eventverse.feature_auth.domain.LoginResult
import com.eneskayiklik.eventverse.feature_auth.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: AuthRepository
) {
    suspend fun loginWithEmail(
        email: String,
        password: String
    ): LoginResult {
        val emailErr =
            if (email.isValidEmail().not()) context.getString(R.string.email_error) else ""
        val passwordErr =
            if (password.isValidPassword().not()) context.getString(R.string.password_error) else ""
        if (emailErr.isNotEmpty() || passwordErr.isNotEmpty()) {
            return LoginResult(emailErr, passwordErr)
        }
        return repository.loginWithEmail(email, password)
    }
}