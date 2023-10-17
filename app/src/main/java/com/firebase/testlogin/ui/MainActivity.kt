package com.firebase.testlogin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.firebase.testlogin.R
import com.firebase.testlogin.databinding.MainActivityBinding
import com.firebase.testlogin.ui.list.BordListActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

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

        }
    }


}