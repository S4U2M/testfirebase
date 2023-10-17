package com.firebase.testlogin.data.repo.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firebase.testlogin.ui.Model
import com.firebase.testlogin.data.model.remote.FireModel
import com.firebase.testlogin.unit.Unit.currentTemplate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class FireBaseRepoImpl() : FireBaseRepo {

    override suspend fun getDataFromTemplate(template: String, user: String): List<FireModel> {
        val database = Firebase.database

        try {
            val snapshot = database.getReference("$user/$template/title").get().await()
            Log.d("템플릿.repo", "$user/$template/title")

            if (snapshot.exists()) {
                val dataList = mutableListOf<FireModel>()

                for (userSnapshot in snapshot.children) {
                    val itemKey = userSnapshot.key ?: ""
                    val getData = userSnapshot.getValue(Model::class.java)

                    getData?.let { model ->

                        val fireModel =
                            FireModel(
                                key = itemKey,
                                title = model.title
                            )

                        dataList.add(fireModel)
                    }
                }
                return dataList
            } else {
                return emptyList()
            }
        } catch (e: Exception) {
            // 여기서 예외 처리 코드를 작성합니다.
            Log.e("템플릿.repo", "데이터 가져오기 중 오류 발생: ${e.message}")
            return emptyList() // 또는 예외 처리에 따른 적절한 반환 값 설정
        }
    }

    override suspend fun deleteItem(
        template: String,
        user: String,
        item: FireModel
    ): List<FireModel> {
        val database = Firebase.database
        val myRef = database.getReference("$user/$template/title")
        myRef.child(item.key).removeValue()

        try {
            val snapshot = database.getReference("$user/$template/title").get().await()
            Log.d("템플릿.repo", "$user/$template/title")

            if (snapshot.exists()) {
                val dataList = mutableListOf<FireModel>()

                for (userSnapshot in snapshot.children) {
                    val itemKey = userSnapshot.key ?: ""
                    val getData = userSnapshot.getValue(Model::class.java)

                    getData?.let { model ->

                        val fireModel =
                            FireModel(
                                key = itemKey,
                                title = model.title
                            )

                        dataList.add(fireModel)
                    }
                }
                return dataList
            } else {
                return emptyList()
            }
        } catch (e: Exception) {
            // 여기서 예외 처리 코드를 작성합니다.
            Log.e("템플릿.repo", "데이터 가져오기 중 오류 발생: ${e.message}")
            return emptyList() // 또는 예외 처리에 따른 적절한 반환 값 설정
        }
    }

    override suspend fun addItem(template: String, user: String, title: String): List<FireModel> {
        val database = Firebase.database
        val myRef = database.getReference("$user/$template/title")

        myRef.push().setValue(
            Model(title = title)
        )

        try {
            val snapshot = database.getReference("$user/$template/title").get().await()
            Log.d("템플릿.repo", "$user/$template/title")

            if (snapshot.exists()) {
                val dataList = mutableListOf<FireModel>()

                for (userSnapshot in snapshot.children) {
                    val itemKey = userSnapshot.key ?: ""
                    val getData = userSnapshot.getValue(Model::class.java)

                    getData?.let { model ->

                        val fireModel =
                            FireModel(
                                key = itemKey,
                                title = model.title
                            )

                        dataList.add(fireModel)
                    }
                }
                return dataList
            } else {
                return emptyList()
            }
        } catch (e: Exception) {
            // 여기서 예외 처리 코드를 작성합니다.
            Log.e("템플릿.repo", "데이터 가져오기 중 오류 발생: ${e.message}")
            return emptyList() // 또는 예외 처리에 따른 적절한 반환 값 설정
        }
    }

    override fun getUser(): String {
        val user = FirebaseAuth.getInstance().currentUser
        return user?.uid ?: ""
    }


}