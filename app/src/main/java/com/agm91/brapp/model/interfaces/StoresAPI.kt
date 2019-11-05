package com.agm91.brapp.model.interfaces

import com.agm91.brapp.model.Stores
import retrofit2.Call
import retrofit2.http.GET

interface StoresAPI {
    @GET("BR_Android_CodingExam_2015_Server/stores.json")
    fun getStores(): Call<Stores>
}