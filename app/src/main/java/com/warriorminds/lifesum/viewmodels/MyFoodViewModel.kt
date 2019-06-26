package com.warriorminds.lifesum.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.warriorminds.lifesum.models.Food
import com.warriorminds.lifesum.repositories.MyFoodRepository
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MyFoodViewModel @Inject constructor(private val repository: MyFoodRepository): ViewModel(), CoroutineScope {

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val myFood: MutableLiveData<List<Food>> = MutableLiveData()
    val error: MutableLiveData<Boolean> = MutableLiveData()

    fun fetchMyFood() {
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.getMyFoodAsync()
            }
            if (response.isNullOrEmpty()) {
                error.value = error.value?.not() ?: true
            } else {
                myFood.value = response
            }
        }
    }

    fun deleteFood(food: Food) {
        launch {
            withContext(Dispatchers.IO) {
                repository.deleteFoodAsync(food)
            }
        }
    }

    fun saveFood(item: Food) {
        launch {
            withContext(Dispatchers.IO) {
                repository.saveFoodAsync(item)
            }
        }
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}