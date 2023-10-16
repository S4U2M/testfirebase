package com.firebase.testlogin.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.firebase.testlogin.data.model.remote.FireModel
import com.firebase.testlogin.data.repo.FireBaseRepo
import com.firebase.testlogin.data.repo.FireBaseRepoImpl

class ListViewModel(
    private val repo: FireBaseRepo
) : ViewModel() {

    val templateDataMap: MutableMap<String, LiveData<TemplateModel>> = mutableMapOf()

    val liveModelList: LiveData<List<FireModel>> = repo.getData()

    /**
     * 템플릿이 추가 된다면
     * addTemplate("Template1",list1)*/

    fun delete(item: FireModel) {
        repo.deleteItem(item)
    }

    fun addTemplate(templateName: String?, list: List<FireModel>?) {
        if (templateName != null && list != null) {
            val templateData = TemplateModel(list)

            templateDataMap[templateName] = MutableLiveData(templateData)
        }

    }

}

class ListViewModelFactory(

) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(
                FireBaseRepoImpl()
            ) as T
        } else {
            throw IllegalArgumentException("Not found ViewModel class.")
        }
    }
}