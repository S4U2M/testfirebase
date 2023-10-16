package com.firebase.testlogin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.firebase.testlogin.databinding.BordWriteActivityBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BordWriteActivity : AppCompatActivity() {

    private lateinit var binding: BordWriteActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BordWriteActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() = with(binding) {

        btnComplite.setOnClickListener {

            val text = writeDescription.text.toString()

            val database = Firebase.database
            val myRef = database.getReference("borad1")

            myRef.push().setValue(
                Model(title = text)
            )

            finish()

        }

    }

}