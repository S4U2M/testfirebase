package com.firebase.testlogin.ui.list

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.firebase.testlogin.data.model.remote.FireModel
import com.firebase.testlogin.data.repo.firebase.FireBaseRepo
import com.firebase.testlogin.data.repo.firebase.FireBaseRepoImpl
import com.firebase.testlogin.data.repo.room.RoomRepo
import com.firebase.testlogin.data.repo.room.RoomRepoImpl
import com.firebase.testlogin.data.room.TestDataBase

class ListViewModel(
    private val fireRepo: FireBaseRepo,
    private val roomRepo: RoomRepo
) : ViewModel() {

    val templateDataMap: MutableMap<String, LiveData<TemplateModel>> = mutableMapOf()

    val liveModelList: LiveData<List<FireModel>> = fireRepo.getData()

    /**
     * 템플릿이 추가 된다면
     * addTemplate("Template1",list1)*/

    fun delete(item: FireModel) {
        fireRepo.deleteItem(item)
    }

    fun addTemplate(templateName: String?, list: List<FireModel>?) {
        if (templateName != null && list != null) {
            val templateData = TemplateModel(list)

            templateDataMap[templateName] = MutableLiveData(templateData)
        }

    }

}

class ListViewModelFactory(
private val context:Context
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