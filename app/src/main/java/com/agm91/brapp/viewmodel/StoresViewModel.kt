package com.agm91.brapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.agm91.brapp.model.APIResponse
import com.agm91.brapp.model.Stores
import com.agm91.brapp.repo.StoresRepository

class StoresViewModel : ViewModel() {
    private val repository = StoresRepository()

    fun getStores(): LiveData<APIResponse<Stores>> {
        return repository.getStores()
    }
}