package com.firebase.testlogin.ui.write

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.firebase.testlogin.databinding.BordWriteActivityBinding
import com.firebase.testlogin.ui.list.ListViewModel
import com.firebase.testlogin.unit.Unit.currentTemplate

class BordWriteActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_WRITE_TYPE = "extra_write_type"
    }

    private lateinit var binding: BordWriteActivityBinding
    private val writeType by lazy {
        WriteType.from(intent.getStringExtra(EXTRA_WRITE_TYPE))
    }

    private lateinit var viewModel: WriteViewModel

    private lateinit var listViewModel : ListViewModel

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
                    addItem(currentTemplate,text)
                }

                else -> {
                    insertTemplateToRoom(title = text)
                }

            }
            finish()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this@BordWriteActivity,
            WriteViewModelFactory(this@BordWriteActivity)
        )[WriteViewModel::class.java]

        with(viewModel) {


        }

    }

    private fun addItem(template:String,title: String) = with(viewModel) {
        addItemToFireBase(template,title)
    }

    private fun insertTemplateToRoom(title: String) = with(viewModel) {
        insertTemplateToRoom(title)
    }

}