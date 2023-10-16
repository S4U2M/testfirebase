package com.firebase.testlogin.data.repo

import androidx.lifecycle.LiveData
import com.firebase.testlogin.data.remote.FireModel

interface FireBaseRepo {

    fun getData(): LiveData<List<FireModel>>
    fun deleteItem(item: FireModel)

}