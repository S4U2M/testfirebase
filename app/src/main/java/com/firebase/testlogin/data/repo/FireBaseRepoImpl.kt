package com.firebase.testlogin.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firebase.testlogin.ui.Model
import com.firebase.testlogin.data.remote.FireModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FireBaseRepoImpl(

) : FireBaseRepo {

    override fun getData(): LiveData<List<FireModel>> {

        val resultLiveData = MutableLiveData<List<FireModel>>()

        val database = Firebase.database
        val myRef = database.getReference("borad1")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val dataList = mutableListOf<FireModel>()

                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val itemKey = userSnapshot.key ?: ""
                        val getData = userSnapshot.getValue(Model::class.java)

                        getData?.let {
                            val fireModel = FireModel(itemKey, it.title)
                            dataList.add(fireModel)
                        }
                    }

                    resultLiveData.postValue(dataList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        return resultLiveData
    }

    override fun deleteItem(item: FireModel) {
        val database = Firebase.database
        val myRef = database.getReference("borad1")
        myRef.child(item.key).removeValue()
    }

}