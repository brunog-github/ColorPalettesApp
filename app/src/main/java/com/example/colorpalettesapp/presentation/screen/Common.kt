package com.example.colorpalettesapp.presentation.screen

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.example.colorpalettesapp.util.Constants.CLIENT_ID
import com.example.colorpalettesapp.util.Constants.CLIENT_SECRET
import com.example.colorpalettesapp.util.Constants.REDIRECT_URI
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun StartActivityForResult(
    key: Any,
    onResultReceived: (String?) -> Unit,
    onDialogDismissed: (String) -> Unit,
    launcher: (ManagedActivityResultLauncher<Intent, ActivityResult>) -> Unit
) {
    val scope = rememberCoroutineScope()
    val activityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        try {
            if (activityResult.resultCode == Activity.RESULT_OK) {
                val result = activityResult.data?.let { intent ->
                    Auth.GoogleSignInApi.getSignInResultFromIntent(intent)
                }
                val serverAuthCode = result?.signInAccount?.serverAuthCode
                Log.d("StartActivityForResult", "ACCESS TOKEN: $serverAuthCode")
                scope.launch(Dispatchers.IO) {
                    onResultReceived(getAccessToken(authCode = serverAuthCode))
                }
            } else {
                onDialogDismissed("Result not okay")
                Log.d("StartActivityForResult", "RESULT NOT OK")
            }
        } catch (e: Exception) {
            onDialogDismissed(e.message.toString())
            Log.d("StartActivityForResult", e.message.toString())
        }
    }
    LaunchedEffect(key1 = key) {
        launcher(activityLauncher)
    }
}

private fun getAccessToken(authCode: String?): String? {
    val tokenResponse: GoogleTokenResponse = try {
        GoogleAuthorizationCodeTokenRequest(
            NetHttpTransport(),
            GsonFactory(),
            "https://www.googleapis.com/oauth2/v4/token",
            CLIENT_ID,
            CLIENT_SECRET,
            authCode,
            REDIRECT_URI
        ).execute()
    } catch (e: Exception) {
        Log.d("getAccessToken", e.message.toString())
        return null
    }
    return tokenResponse.accessToken
}

fun signIn(
    activity: Activity,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>
) {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestServerAuthCode(CLIENT_ID)
        .build()
    val client = GoogleSignIn.getClient(activity, gso)
    launcher.launch(client.signInIntent)
}