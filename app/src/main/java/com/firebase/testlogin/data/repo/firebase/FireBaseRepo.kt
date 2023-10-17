package com.firebase.testlogin.data.repo.firebase

import androidx.lifecycle.LiveData
import com.firebase.testlogin.data.model.remote.FireModel

interface FireBaseRepo {

    fun getDataFromTemplate(template: String, user: String): LiveData<List<FireModel>>
    fun getAllData(user: String): LiveData<List<FireModel>>
    fun deleteItem(template: String, user: String, item: FireModel)
    fun addItem(template: String, user: String, title: String)
    fun getUser(): String

}