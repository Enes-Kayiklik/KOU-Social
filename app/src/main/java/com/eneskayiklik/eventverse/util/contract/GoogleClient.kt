package com.eneskayiklik.eventverse.util.contract

import android.content.Context
import com.eneskayiklik.eventverse.BuildConfig
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

fun getGoogleLoginClient(context: Context): GoogleSignInClient {
    val signInOptions = GoogleSignInOptions.Builder()
        .requestEmail()
        .requestProfile()
        .requestIdToken(BuildConfig.CLIENT_ID) // WEB Client id from google console see: https://android-developers.googleblog.com/2016/03/registering-oauth-clients-for-google.html

        .build()
    return GoogleSignIn.getClient(context, signInOptions)
}