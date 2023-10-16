package com.firebase.testlogin.ui.write

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.firebase.testlogin.databinding.BordWriteActivityBinding

class BordWriteActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_WRITE_TYPE = "extra_write_type"
    }

    private lateinit var binding: BordWriteActivityBinding
    private val writeType by lazy {
        WriteType.from(intent.getStringExtra(EXTRA_WRITE_TYPE))
    }

    private lateinit var viewModel: WriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BordWriteActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("enumtype", writeType.toString())

        initView()
        initViewModel()
    }

    private fun initView() = with(binding) {

        btnComplite.setOnClickListener {

            val text = writeDescription.text.toString()


            when (writeType) {
                WriteType.ADD -> {
                    addItem(text)
                }

                else -> {
                    //템플릿 추가
                }

            }





            finish()

        }

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this@BordWriteActivity,
            WriteViewModelFactory()
        )[WriteViewModel::class.java]

        with(viewModel) {


        }

    }

    private fun addItem(title: String) = with(viewModel) {
        addItem(title)
    }

}