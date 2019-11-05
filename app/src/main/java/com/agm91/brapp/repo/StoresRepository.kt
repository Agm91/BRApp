package com.agm91.brapp.repo

import androidx.lifecycle.MutableLiveData
import com.agm91.brapp.model.APIResponse
import com.agm91.brapp.model.Stores
import com.agm91.brapp.model.interfaces.StoresAPI
import com.agm91.brapp.util.MyApp
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StoresRepository {
    private val data = MutableLiveData<APIResponse<Stores>>()
    private val storesAPI: StoresAPI

    init {
        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(MyApp.instance.cacheDir, cacheSize)

        val okHttpClient = OkHttpClient.Builder()
            .cache(myCache)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://sandbox.bottlerocketapps.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        storesAPI = retrofit.create(StoresAPI::class.java)
    }

    fun getStores(): MutableLiveData<APIResponse<Stores>> {
        storesAPI.getStores().enqueue(object : Callback<Stores> {
            override fun onFailure(call: Call<Stores>, t: Throwable) {
                data.value = APIResponse(data = null, error = t)
            }

            override fun onResponse(call: Call<Stores>, response: Response<Stores>) {
                data.value = APIResponse(data = response.body(), error = null)
            }
        })
        return data
    }
}