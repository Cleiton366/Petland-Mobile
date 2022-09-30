package com.example.petland_mobile

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.petland_mobile.models.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import kotlinx.coroutines.runBlocking

const val RC_SIGN_IN = 123

class MainActivity : AppCompatActivity() {
    private lateinit var user : User
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestProfile()
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val googleSignInBtn : LinearLayout = findViewById(R.id.googleSignInBtn)
        googleSignInBtn.setOnClickListener{
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        // The Task returned from this call is always completed, no need to attach
        // a listener.
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        handleSignInResult(task)

    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            Toast.makeText(this, "Failed to Log In:$e", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        fetchUser(account)
        if(user.id != "") {
            val intent = Intent(this,  HomeActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Failed to Log In", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchUser(account : GoogleSignInAccount) {
        runBlocking {
            val url = getString(R.string.server) + "/user-info"
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    gson()
                }
            }
            val res: HttpResponse = client.get(url) {
                headers {
                    append("user", account.id.toString())
                }
            }
            //if user is found on DB, create session and go to home activity
            if(res.status.value == 200) {
                user = res.body()
                client.close()
            } else {
                client.close()
                createNewUser(account)
            }
        }
    }

    private fun createNewUser( account: GoogleSignInAccount) {
        runBlocking {
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    gson()
                }
            }
            val res : HttpResponse = client.post(getString(R.string.server)+"/newUserAndroid") {
                contentType(ContentType.Application.Json)
                setBody(User(
                    account.id!!,
                    account.displayName!!,
                    account.email!!,
                    account.photoUrl.toString()
                ))
            }
            if(res.status.value == 200) {
                user = res.body()
                client.close()
            }
        }
    }
}