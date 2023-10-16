package com.firebase.testlogin.ui.write

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.firebase.testlogin.data.repo.firebase.FireBaseRepo
import com.firebase.testlogin.data.repo.firebase.FireBaseRepoImpl

class WriteViewModel(
    private val repo: FireBaseRepo
) : ViewModel() {

    fun addItem(title: String) = with(repo) {
        addItem(title)
    }

}

class WriteViewModelFactory(

) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WriteViewModel::class.java)) {
            return WriteViewModel(
                FireBaseRepoImpl()
            ) as T
        } else {
            throw IllegalArgumentException("Not found ViewModel class.")
        }
    }
}