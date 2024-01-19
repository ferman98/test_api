package com.ferman.myapplication

import com.ferman.myapplication.model.Response
import com.ferman.myapplication.model.ResponseItem
import retrofit2.http.GET

interface Api {

    @GET("posts")
    suspend fun getData(): retrofit2.Response<List<ResponseItem>>
}