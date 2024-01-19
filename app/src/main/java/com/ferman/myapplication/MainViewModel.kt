package com.ferman.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ferman.myapplication.model.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    fun getDatas(): LiveData<Status> {
        return flow {
            emit(Status.Loading)
            val data = Retrofit
                .Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
                .getData()
            if (data.isSuccessful) {
                val res = data.body()?.response
                if (!res.isNullOrEmpty()) {
                    emit(Status.Success(data.body()))
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


//                .enqueue(object : Callback<Response> {
//                    override fun onResponse(
//                        call: Call<Response>,
//                        response: retrofit2.Response<Response>,
//                    ) {
//                        emit(Status.Success(response))
//                    }
//
//                    override fun onFailure(call: Call<Response>, t: Throwable) {
//                        emit(Status.Failed)
//                    }
//                })