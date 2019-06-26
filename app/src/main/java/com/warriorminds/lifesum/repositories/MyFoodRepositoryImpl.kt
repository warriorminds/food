package com.warriorminds.lifesum.repositories

import com.warriorminds.lifesum.db.FoodDatabase
import com.warriorminds.lifesum.models.Food
import javax.inject.Inject

class MyFoodRepositoryImpl @Inject constructor(private val database: FoodDatabase) : MyFoodRepository {
    override suspend fun saveFoodAsync(food: Food) = database.foodDao().insert(food)

    override suspend fun getMyFoodAsync() = database.foodDao().load()

    override suspend fun deleteFoodAsync(food: Food) {
        database.foodDao().delete(food.id)
    }
}