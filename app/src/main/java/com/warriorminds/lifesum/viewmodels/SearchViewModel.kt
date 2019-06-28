package com.warriorminds.lifesum.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.warriorminds.lifesum.models.Food
import com.warriorminds.lifesum.repositories.MyFoodRepository
import com.warriorminds.lifesum.repositories.SearchRepository
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository,
                                          private val myFoodRepository: MyFoodRepository): ViewModel(), CoroutineScope {
    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val food: MutableLiveData<List<Food>> = MutableLiveData()
    val fetchingError: MutableLiveData<Boolean> = MutableLiveData()
    val noResults: MutableLiveData<Boolean> = MutableLiveData()
    val savingState: MutableLiveData<Boolean> = MutableLiveData()
    var searchTerm: String = ""

    fun searchFood() {
        launch {
            val response = withContext(Dispatchers.IO) {
                searchRepository.searchFoodAsync(searchTerm)
            }

            if (response != null && response.isNotEmpty()) {
                food.value = response
            } else if (response != null && response.isEmpty()) {
                noResults.value = noResults.value?.not() ?: true
            } else {
                fetchingError.value = fetchingError.value?.not() ?: true
            }
        }
    }

    fun saveFood(item: Food) {
        launch {
            val response = withContext(Dispatchers.IO) {
                myFoodRepository.saveFoodAsync(item)
            }
            savingState.value = response > 0
        }
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}