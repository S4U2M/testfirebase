package com.firebase.testlogin.data.repo.firebase

import androidx.lifecycle.LiveData
import com.firebase.testlogin.data.model.remote.FireModel

interface FireBaseRepo {

    fun getDataFromTemplate(user: String): LiveData<List<FireModel>>
    fun deleteItem(user: String,item: FireModel)
    fun addItem(user: String, title: String)
    fun getUser(): String

}