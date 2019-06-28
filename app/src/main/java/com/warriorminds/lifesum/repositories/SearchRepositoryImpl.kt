package com.warriorminds.lifesum.repositories

import com.warriorminds.lifesum.models.Food
import com.warriorminds.lifesum.network.FoodService
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val service: FoodService,
                                               private val locale: Locale): SearchRepository {

    override suspend fun searchFoodAsync(query: String): List<Food>? {
        try {
            val response = service.searchFood(locale.getLanguage(), locale.getCountry(), query).await()
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