package com.firebase.testlogin.ui.write

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.firebase.testlogin.data.model.local.TemplateEntity
import com.firebase.testlogin.data.repo.firebase.FireBaseRepo
import com.firebase.testlogin.data.repo.firebase.FireBaseRepoImpl
import com.firebase.testlogin.data.repo.room.RoomRepo
import com.firebase.testlogin.data.repo.room.RoomRepoImpl
import com.firebase.testlogin.data.room.TestDataBase
import kotlinx.coroutines.launch

class WriteViewModel(
    private val fireRepo: FireBaseRepo,
    private val roomRepo: RoomRepo
) : ViewModel() {

    fun addItemToFireBase(template: String, title: String) = with(fireRepo) {

        viewModelScope.launch {
            addItem(template, fireRepo.getUser(), title)
        }

    }

    fun insertTemplateToRoom(title: String) = with(roomRepo) {
        viewModelScope.launch {
            insertItemToRoom(
                TemplateEntity(
                    id = 0,
                    title = title
                )
            )
        }
    }

}

class WriteViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WriteViewModel::class.java)) {
            return WriteViewModel(
                FireBaseRepoImpl(),
                RoomRepoImpl(TestDataBase.getDatabase(context))
            ) as T
        } else {
            throw IllegalArgumentException("Not found ViewModel class.")
        }
    }
}