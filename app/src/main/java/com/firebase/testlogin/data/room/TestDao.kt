package com.firebase.testlogin.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.firebase.testlogin.data.model.local.TemplateEntity



@Dao
interface TestDao {

    @Query("SELECT * FROM test_table")
    fun getAllData(): LiveData<List<TemplateEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(item: TemplateEntity)

    @Query("DELETE FROM test_table WHERE id = :id")
    suspend fun deleteTestEntityById(id: Int)

}