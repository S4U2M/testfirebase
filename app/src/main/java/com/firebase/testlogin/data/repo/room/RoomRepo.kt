package com.firebase.testlogin.data.repo.room

import androidx.lifecycle.LiveData
import com.firebase.testlogin.data.model.local.TemplateEntity

interface RoomRepo {

    fun getAllDataFromRoom():LiveData<List<TemplateEntity>>

    suspend fun insertItemToRoom(item:TemplateEntity)

    suspend fun deleteItemFromRoom(item:TemplateEntity)

}