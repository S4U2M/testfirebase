package com.firebase.testlogin.ui.list

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.firebase.testlogin.databinding.BordListActivityBinding
import com.firebase.testlogin.ui.write.BordWriteActivity
import com.firebase.testlogin.ui.Model
import com.firebase.testlogin.data.model.remote.FireModel
import com.firebase.testlogin.ui.list.adapter.MyListAdapter
import com.firebase.testlogin.ui.write.BordWriteActivity.Companion.EXTRA_WRITE_TYPE
import com.firebase.testlogin.ui.write.WriteType

class BordListActivity : AppCompatActivity() {

    private lateinit var binding: BordListActivityBinding
    private val adapter by lazy {
        MyListAdapter(
            onItemClicked = { item ->

                alterDialog(item)

            }
        )
    }

    private lateinit var viewModel: ListViewModel
    val list = mutableListOf<Model>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BordListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initViewModel()
    }

    private fun initView() = with(binding) {

        writeList.adapter = adapter

        writeBtn.setOnClickListener {
            val intent = Intent(this@BordListActivity, BordWriteActivity::class.java).apply {
                putExtra(EXTRA_WRITE_TYPE, WriteType.ADD.name)
            }
            startActivity(intent)
        }

        templateBtn.setOnClickListener{
            val intent = Intent(this@BordListActivity, BordWriteActivity::class.java).apply {
                putExtra(EXTRA_WRITE_TYPE, WriteType.TEMPLATE.name)
            }
            startActivity(intent)
        }


    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this@BordListActivity,
            ListViewModelFactory()
        )[ListViewModel::class.java]

        with(viewModel) {
            liveModelList.observe(this@BordListActivity, Observer { newData ->
                adapter.submitList(newData)
            })
        }

    }

    private fun alterDialog(item: FireModel) {
        val builder = AlertDialog.Builder(this@BordListActivity)
        builder.setTitle("삭제")
        builder.setMessage("${item.title}을(를) 삭제 할까요?")
        builder.setNegativeButton("예") { _, _ ->
            deleteClicked(item)
        }
        builder.setPositiveButton("아니오") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun deleteClicked(item: FireModel) = with(viewModel) {
        delete(item)
    }


}