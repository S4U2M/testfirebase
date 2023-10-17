package com.firebase.testlogin.ui.list

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.firebase.testlogin.data.model.local.TemplateEntity
import com.firebase.testlogin.databinding.BordListActivityBinding
import com.firebase.testlogin.ui.write.BordWriteActivity
import com.firebase.testlogin.ui.Model
import com.firebase.testlogin.data.model.remote.FireModel
import com.firebase.testlogin.ui.list.adapter.MyListAdapter
import com.firebase.testlogin.ui.list.adapter.TemplateAdapter
import com.firebase.testlogin.ui.write.BordWriteActivity.Companion.EXTRA_WRITE_TYPE
import com.firebase.testlogin.ui.write.WriteType
import com.firebase.testlogin.unit.Unit.currentTemplate
import kotlin.math.log

class BordListActivity : AppCompatActivity() {

    private lateinit var binding: BordListActivityBinding

    private val listAdapter by lazy {
        MyListAdapter(
            onItemClicked = { item ->

                alterDialog(item)

            }
        )
    }

    private val templateAdapter by lazy {
        TemplateAdapter(
            onItemClicked = { template ->
                currentTemplate = "${template.title}-${template.id.toString()}"
                updateFireBase(currentTemplate)

                Toast.makeText(
                    this@BordListActivity,
                    "$currentTemplate",
                    Toast.LENGTH_SHORT
                ).show()
            },
            onLongItemClicked = { template ->
                deleteFromRoom(template)
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

    override fun onResume() {
        super.onResume()

        updateResume(currentTemplate)
    }

    private fun initView() = with(binding) {

        templateList.adapter = templateAdapter
        writeList.adapter = listAdapter

        writeBtn.setOnClickListener {
            val intent = Intent(this@BordListActivity, BordWriteActivity::class.java).apply {
                putExtra(EXTRA_WRITE_TYPE, WriteType.ADD.name)
            }
            startActivity(intent)
        }

        templateBtn.setOnClickListener {
            val intent = Intent(this@BordListActivity, BordWriteActivity::class.java).apply {
                putExtra(EXTRA_WRITE_TYPE, WriteType.TEMPLATE.name)
            }
            startActivity(intent)
        }


    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this@BordListActivity,
            ListViewModelFactory(this@BordListActivity)
        )[ListViewModel::class.java]


        with(viewModel) {
            liveModelList.observe(this@BordListActivity, Observer { newData ->
                if (newData != null) {
                    listAdapter.submitList(newData)

                    Log.d("템플릿.observe", newData.toString())
                }

            })

            liveTemplateList.observe(this@BordListActivity, Observer { newData ->
                templateAdapter.submitList(newData)
            })
        }

    }

    private fun alterDialog(item: FireModel) {
        val builder = AlertDialog.Builder(this@BordListActivity)
        builder.setTitle("삭제")
        builder.setMessage("${item.title}을(를) 삭제 할까요?")
        builder.setNegativeButton("예") { _, _ ->
            deleteClicked(currentTemplate, item)
        }
        builder.setPositiveButton("아니오") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun deleteClicked(template: String, item: FireModel) = with(viewModel) {
        delete(template, item)
    }

    private fun deleteFromRoom(item: TemplateEntity) = with(viewModel) {
        deleteFromRoom(item)
    }

    private fun updateFireBase(template: String) = with(viewModel) {
        updateModelList(template)
    }

    private fun updateResume(template: String) = with(viewModel) {
        updateModelList(currentTemplate)
    }


}