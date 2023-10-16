package com.firebase.testlogin.data.repo.firebase

import androidx.lifecycle.LiveData
import com.firebase.testlogin.data.model.remote.FireModel
import com.firebase.testlogin.ui.Model

interface FireBaseRepo {

    fun getData(): LiveData<List<FireModel>>
    fun deleteItem(item: FireModel)

    fun addItem(title: String)

}