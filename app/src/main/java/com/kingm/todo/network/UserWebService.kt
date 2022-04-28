package com.kingm.todo.network

import retrofit2.Response
import retrofit2.http.*

interface UserWebService {
    @GET("users/info")
    suspend fun getInfo(): Response<UserInfo>
}