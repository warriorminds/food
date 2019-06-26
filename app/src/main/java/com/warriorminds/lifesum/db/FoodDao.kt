package com.warriorminds.lifesum.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.warriorminds.lifesum.models.Food

@Dao
interface FoodDao {
    @Insert(onConflict = REPLACE)
    fun insert(food: Food): Long

    @Query(value = "SELECT * FROM food ORDER BY title ASC")
    fun load(): List<Food>

    @Query(value = "DELETE FROM food WHERE id = :id")
    fun delete(id: Int)
}