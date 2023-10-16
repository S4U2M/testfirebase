package com.firebase.testlogin.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.firebase.testlogin.data.remote.FireModel
import com.firebase.testlogin.data.repo.FireBaseRepo
import com.firebase.testlogin.data.repo.FireBaseRepoImpl

class ListViewModel(
    private val repo: FireBaseRepo
) : ViewModel() {

    val liveModelList: LiveData<List<FireModel>> = repo.getData()

    fun delete(item: FireModel){
        repo.deleteItem(item)
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