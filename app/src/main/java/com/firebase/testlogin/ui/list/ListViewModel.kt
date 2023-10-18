package com.firebase.testlogin.ui.list

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.firebase.testlogin.data.model.local.TemplateEntity
import com.firebase.testlogin.data.model.remote.FireModel
import com.firebase.testlogin.data.repo.firebase.FireBaseRepo
import com.firebase.testlogin.data.repo.firebase.FireBaseRepoImpl
import com.firebase.testlogin.data.repo.room.RoomRepo
import com.firebase.testlogin.data.repo.room.RoomRepoImpl
import com.firebase.testlogin.data.room.TestDataBase
import com.firebase.testlogin.unit.Unit.currentTemplate
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ListViewModel(
    private val fireRepo: FireBaseRepo,
    private val roomRepo: RoomRepo
) : ViewModel() {

    private val _liveModelList: MutableLiveData<List<FireModel>> = MutableLiveData()
    val liveModelList: LiveData<List<FireModel>> get() = _liveModelList

    val liveTemplateList: LiveData<List<TemplateEntity>> get() = roomRepo.getAllDataFromRoom()


    fun updateModelList(template: String) {
        viewModelScope.launch {
            val dataList = fireRepo.getDataFromTemplate(template, fireRepo.getUser())
            _liveModelList.value = dataList
            Log.d("템플릿", currentTemplate)
            Log.d("템플릿.업데이트", dataList.toString())
        }

    }

    fun delete(template: String, item: FireModel) {
        viewModelScope.launch {
            val list = fireRepo.deleteItem(template, fireRepo.getUser(), item)
            _liveModelList.value = list
        }

    }

    fun deleteFromRoom(item: TemplateEntity) {
        viewModelScope.launch {
            roomRepo.deleteItemFromRoom(item)
        }
    }

}

class ListViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(
                FireBaseRepoImpl(
                    auth = FirebaseAuth.getInstance()
                ),
                RoomRepoImpl(TestDataBase.getDatabase(context))
            ) as T
        } else {
            throw IllegalArgumentException("Not found ViewModel class.")
        }
    }
}