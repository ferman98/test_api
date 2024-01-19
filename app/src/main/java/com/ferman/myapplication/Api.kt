package com.ferman.myapplication

import com.ferman.myapplication.model.Response
import retrofit2.http.GET

interface Api {

    @GET("post")
    suspend fun getData(): retrofit2.Response<Response>
}