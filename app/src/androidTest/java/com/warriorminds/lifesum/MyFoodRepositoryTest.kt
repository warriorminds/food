package com.warriorminds.lifesum

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.warriorminds.lifesum.db.FoodDatabase
import com.warriorminds.lifesum.models.Food
import com.warriorminds.lifesum.repositories.MyFoodRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MyFoodRepositoryTest {

    private lateinit var db: FoodDatabase
    private lateinit var repository: MyFoodRepositoryImpl

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, FoodDatabase::class.java).build()
        repository = MyFoodRepositoryImpl(db)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    fun saveFoodAsyncTest() {
        runBlocking {
            repository.saveFoodAsync(createFood("name", 1))
            repository.saveFoodAsync(createFood("name2", 2))
            val retrievedFood = repository.getMyFoodAsync()
            assertNotNull(retrievedFood)
            assertEquals(2, retrievedFood.size)
            assertEquals("name", retrievedFood[0].name)
        }
    }

    @Test
    fun deleteFoodAsyncTest() {
        runBlocking {
            val foodOne = createFood("name", 1)
            repository.saveFoodAsync(foodOne)
            repository.saveFoodAsync(createFood("name2", 2))
            repository.deleteFoodAsync(foodOne)
            val retrievedFood = repository.getMyFoodAsync()
            assertNotNull(retrievedFood)
            assertEquals(1, retrievedFood.size)
            assertEquals("name2", retrievedFood[0].name)
        }
    }

    private fun createFood(name: String, id: Int) = Food(title = "testFood",
        calories = 123,
        protein = 123.456f,
        carbohydrates = 1.0f,
        brand = "brand",
        category = "category",
        cholesterol = 0f,
        fat = 321f,
        fiber = 0.0f,
        id = id,
        gramsPerServing = null,
        measurementId = null,
        name = name,
        potassium = null,
        saturatedFat = null,
        unsaturatedFat = null,
        servingCategory = null,
        sugar = null,
        sodium = null)

}