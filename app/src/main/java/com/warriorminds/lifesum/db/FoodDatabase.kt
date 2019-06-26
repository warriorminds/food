package com.warriorminds.lifesum.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.warriorminds.lifesum.models.Food

@Database(entities = [Food::class], version = 1)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
}