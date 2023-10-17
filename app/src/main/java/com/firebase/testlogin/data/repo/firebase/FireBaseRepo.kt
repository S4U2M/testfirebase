package com.firebase.testlogin.data.repo.firebase

import androidx.lifecycle.LiveData
import com.firebase.testlogin.data.model.remote.FireModel

interface FireBaseRepo {

    suspend fun getDataFromTemplate(template: String, user: String): List<FireModel>
    suspend fun deleteItem(template: String, user: String, item: FireModel): List<FireModel>
    suspend fun addItem(template: String, user: String, title: String): List<FireModel>
    fun getUser(): String

}