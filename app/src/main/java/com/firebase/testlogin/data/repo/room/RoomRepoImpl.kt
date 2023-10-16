package com.firebase.testlogin.data.repo.room

import androidx.lifecycle.LiveData
import com.firebase.testlogin.data.model.local.TemplateEntity
import com.firebase.testlogin.data.room.TestDataBase

class RoomRepoImpl(
    private val database: TestDataBase?
) : RoomRepo {
    override fun getAllDataFromRoom(): LiveData<List<TemplateEntity>> {
        val dao = database?.testDao() ?: throw IllegalStateException("test fail")

        return dao.getAllData()
    }

    override suspend fun insertItemToRoom(item: TemplateEntity) {
        val dao = database?.testDao() ?: throw IllegalStateException("test fail")

        dao.insertItem(item)
    }

    override suspend fun deleteItemFromRoom(item: TemplateEntity) {
        val dao = database?.testDao() ?: throw IllegalStateException("test fail")

        dao.deleteTestEntityById(item.id)
    }


}