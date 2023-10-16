package com.firebase.testlogin.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.firebase.testlogin.data.model.local.TestEntity

@Dao
interface TestDao {

    @Query("SELECT * FROM test_table")
    fun getAllData(): LiveData<List<TestEntity>>

    @Insert
    suspend fun insertItem(item: TestEntity)

    @Query("DELETE FROM test_table WHERE id = :id")
    suspend fun deleteTestEntityById(id: Int)


}