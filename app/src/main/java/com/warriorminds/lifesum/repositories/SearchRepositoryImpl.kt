package com.warriorminds.lifesum.repositories

import com.warriorminds.lifesum.models.Food
import com.warriorminds.lifesum.network.FoodService
import java.util.*
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val service: FoodService): SearchRepository {

    override suspend fun searchFoodAsync(query: String): List<Food>? {
        try {
            val response = service.searchFood(Locale.getDefault().language, Locale.getDefault().country, query).await()
            if (response.isSuccessful) {
                if (response.body() != null) {
                    return response.body()!!.food
                }
                return mutableListOf()
            }
            return null
        } catch (e: Exception) {
            return null
        }
    }
}