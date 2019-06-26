package com.warriorminds.lifesum.network

import com.warriorminds.lifesum.models.FoodResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FoodService {
    @GET("icebox/v1/foods/{language}/{country}/{query}")
    fun searchFood(@Path("language") language: String,
                   @Path("country") country: String,
                   @Path("query") query: String): Deferred<Response<FoodResponse>>
}