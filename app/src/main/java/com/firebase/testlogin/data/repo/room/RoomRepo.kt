package com.firebase.testlogin.data.repo.room

import androidx.lifecycle.LiveData
import com.firebase.testlogin.data.model.local.TestEntity

interface RoomRepo {

    fun getAllDataFromRoom():LiveData<List<TestEntity>>

    suspend fun insertItemToRoom(item:TestEntity)

    suspend fun deleteItemFromRoom(item:TestEntity)

}