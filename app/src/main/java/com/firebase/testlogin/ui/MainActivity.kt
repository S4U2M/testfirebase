package com.firebase.testlogin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.firebase.testlogin.R
import com.firebase.testlogin.databinding.MainActivityBinding
import com.firebase.testlogin.ui.list.BordListActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var launcher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { result ->

                if (result.resultCode == RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    handleGoogleSignInResult(task)
                }

            })

    }

    private fun initView() = with(binding) {

        auth = Firebase.auth

        Toast.makeText(this@MainActivity, auth.currentUser?.uid.toString(), Toast.LENGTH_SHORT)
            .show()

        homeBtnSignin.setOnClickListener {
            val email = homeLoginEmail.text.toString()
            val password = homeLoginPassword.text.toString()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this@MainActivity) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@MainActivity, "ok", Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(this@MainActivity, "no", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        homeBtnLogout.setOnClickListener {
            auth.signOut()
            Toast.makeText(this@MainActivity, auth.currentUser?.uid.toString(), Toast.LENGTH_SHORT)
                .show()

        }

        homeBtnLogin.setOnClickListener {
            val email = homeLoginEmail.text.toString()
            val password = homeLoginPassword.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this@MainActivity) { task ->

                    if (task.isSuccessful) {
                        Toast.makeText(this@MainActivity, "ok", Toast.LENGTH_SHORT).show()
                        Toast.makeText(
                            this@MainActivity,
                            auth.currentUser?.uid.toString(),
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(this@MainActivity, BordListActivity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this@MainActivity, "no", Toast.LENGTH_SHORT).show()

                    }
                }
        }


        homeGoggleLogin.setOnClickListener {

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_url))
                .requestEmail()
                .build()

            val googleSignInClient: GoogleSignInClient =
                GoogleSignIn.getClient(this@MainActivity, gso)

            val signInIntent = googleSignInClient.signInIntent

            launcher.launch(signInIntent)
        }
    }

    private fun handleGoogleSignInResult(task: Task<GoogleSignInAccount>) {
        try {

            val account = task.getResult(ApiException::class.java)
            val idToken = account?.idToken

            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this@MainActivity) { firebaseTask ->
                    if (firebaseTask.isSuccessful) {
                        Toast.makeText(
                            this@MainActivity,
                            "Google login successful",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@MainActivity, BordListActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Firebase login failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } catch (e: ApiException) {
            Toast.makeText(this@MainActivity, "Google login failed", Toast.LENGTH_SHORT).show()
        }
    }

}