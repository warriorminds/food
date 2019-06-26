package com.warriorminds.lifesum.repositories

import com.warriorminds.lifesum.models.Food

interface SearchRepository {
    suspend fun searchFoodAsync(query: String): List<Food>?
}