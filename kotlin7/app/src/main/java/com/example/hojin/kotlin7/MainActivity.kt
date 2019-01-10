package com.example.hojin.kotlin7

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.util.Log
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val RC_SIGN_IN = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var auth = FirebaseAuth.getInstance()
        Log.i("oncreate",auth.currentUser.toString())
        if (auth.currentUser!= null) {
            //never executes
            // signed in
            setupActivity()
        } else {
            // not signed in
            createFirebaseLoginUI()
        }
    }
    fun setupActivity(){

        setContentView(R.layout.activity_main)

        val auth = FirebaseAuth.getInstance()
        text_view1.text= String.format("Hello %s", auth.currentUser!!.displayName)
        button1.setOnClickListener{
            AuthUI.getInstance()
                    .signOut(applicationContext)
                            finish()
        }
    }
    fun createFirebaseLoginUI(){



        // Choose authentication providers
        var providers = Arrays.asList<AuthUI.IdpConfig>(
                //new AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build())

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false)
                        .setLogo(R.drawable.ic_assets_washingmachinedark)
                        .setTosAndPrivacyPolicyUrls(
                                "https://termsfeed.com/blog/sample-terms-of-service-template/",
                                "https://www.freeprivacypolicy.com/blog/sample-privacy-policy-template/")
                        .build(),
                RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("im here",requestCode.toString()+resultCode.toString())
        if (requestCode == RC_SIGN_IN) {
            var response = IdpResponse.fromResultIntent(data)

            if (resultCode == RESULT_OK) {
                // Successfully signed in

                val auth = FirebaseAuth.getInstance()
                val user = auth.currentUser

                val toast_success = Toast.makeText(applicationContext, "Logged in: " + user!!.displayName!!, Toast.LENGTH_SHORT)
                toast_success.show()

                setupActivity()

            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                val toast = Toast.makeText(applicationContext, "! Error: " + response!!.error!!.errorCode, Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }

}
