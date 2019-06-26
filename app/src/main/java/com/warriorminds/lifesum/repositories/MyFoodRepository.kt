package com.warriorminds.lifesum.repositories

import com.warriorminds.lifesum.models.Food

interface MyFoodRepository {
    suspend fun getMyFoodAsync(): List<Food>

    suspend fun saveFoodAsync(food: Food): Long

    suspend fun deleteFoodAsync(food: Food)
}