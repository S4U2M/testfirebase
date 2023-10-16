package com.firebase.testlogin.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.firebase.testlogin.data.model.local.TemplateEntity

@Database(entities = [TemplateEntity::class], version = 2)
abstract class TestDataBase : RoomDatabase() {
    abstract fun testDao(): TestDao

    companion object {
        @Volatile
        private var INSTANCE: TestDataBase? = null

        fun getDatabase(
            context: Context
        ): TestDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TestDataBase::class.java,
                    "test_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }

}