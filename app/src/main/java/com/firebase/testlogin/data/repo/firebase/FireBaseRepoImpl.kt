package com.firebase.testlogin.data.repo.firebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firebase.testlogin.ui.Model
import com.firebase.testlogin.data.model.remote.FireModel
import com.firebase.testlogin.unit.Unit.currentTemplate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FireBaseRepoImpl(

) : FireBaseRepo {

    override fun getDataFromTemplate(template:String,user: String): LiveData<List<FireModel>> {

        val resultLiveData = MutableLiveData<List<FireModel>>()

        val database = Firebase.database
        val myRef = database.getReference("$user/$template/title")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val dataList = mutableListOf<FireModel>()

                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val itemKey = userSnapshot.key ?: ""
                        val getData = userSnapshot.getValue(Model::class.java)

                        getData?.let { model ->

                            val fireModel =
                                FireModel(
                                    key = itemKey,
                                    title = model.title
                                )

                            dataList.add(fireModel)
                        }
                    }

                    resultLiveData.postValue(dataList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // 데이터베이스 작업이 실패한 경우 처리
            }
        })

        return resultLiveData
    }

    override fun getAllData(user: String): LiveData<List<FireModel>> {
        val resultLiveData = MutableLiveData<List<FireModel>>()

        val database = Firebase.database
        val myRef = database.getReference("$user/tempalate1-16/title")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val dataList = mutableListOf<FireModel>()

                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val itemKey = userSnapshot.key ?: ""
                        val getData = userSnapshot.getValue(Model::class.java)

                        getData?.let { model ->

                            val fireModel =
                                FireModel(
                                    key = itemKey,
                                    title = model.title
                                )

                            dataList.add(fireModel)
                        }
                    }

                    resultLiveData.postValue(dataList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // 데이터베이스 작업이 실패한 경우 처리
            }
        })

        return resultLiveData
    }


    override fun deleteItem(template: String,user: String,item: FireModel) {
        val database = Firebase.database
        val myRef = database.getReference("$user/$template/title")
        myRef.child(item.key).removeValue()
    }

    override fun addItem(template: String,user: String, title: String) {
        val database = Firebase.database
        val myRef = database.getReference("$user/$template/title")

        myRef.push().setValue(
            Model(title = title)
        )
    }

    override fun getUser(): String {
        val user = FirebaseAuth.getInstance().currentUser
        return user?.uid ?: ""
    }


}