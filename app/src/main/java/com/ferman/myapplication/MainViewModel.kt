package com.ferman.myapplication

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.ferman.myapplication.model.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    fun getDatas(context: Context): LiveData<Status> {
        return flow {
            emit(Status.Loading)

            val chucker = OkHttpClient
                .Builder()
                .addInterceptor(ChuckerInterceptor(context))
            val data = Retrofit
                .Builder()
                .client(chucker.build())
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
                .getData()
            if (data.isSuccessful) {
                val res = data.body()
                if (!res.isNullOrEmpty()) {
                    emit(Status.Success(res))
                } else {
                    emit(Status.Failed)
                }
            } else {
                emit(Status.Failed)
            }
        }
            .flowOn(Dispatchers.IO)
            .asLiveData()
    }
}