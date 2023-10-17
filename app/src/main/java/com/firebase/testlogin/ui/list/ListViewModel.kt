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
import kotlinx.coroutines.launch

class ListViewModel(
    private val fireRepo: FireBaseRepo,
    private val roomRepo: RoomRepo
) : ViewModel() {

    private val _liveModelList: MutableLiveData<List<FireModel>> = MutableLiveData()
    val liveModelList: LiveData<List<FireModel>> get() = _liveModelList
//    val liveModelList: LiveData<List<FireModel>> get() = fireRepo.getAllData(fireRepo.getUser())

    val liveTemplateList: LiveData<List<TemplateEntity>> get() = roomRepo.getAllDataFromRoom()


    /**
     * 템플릿이 추가 된다면
     * addTemplate("Template1",list1)*/
    fun updateModelList(template:String) {
        val dataList = fireRepo.getDataFromTemplate(template,fireRepo.getUser())
        _liveModelList.value = dataList.value
        Log.d("템플릿",currentTemplate)
        Log.d("템플릿.업데이트",dataList.value.toString())
    }

    fun delete(template:String,item: FireModel) {
        fireRepo.deleteItem(template,fireRepo.getUser(), item)
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
                FireBaseRepoImpl(),
                RoomRepoImpl(TestDataBase.getDatabase(context))
            ) as T
        } else {
            throw IllegalArgumentException("Not found ViewModel class.")
        }
    }
}