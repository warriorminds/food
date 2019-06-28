package com.warriorminds.lifesum

import com.google.gson.Gson
import com.warriorminds.lifesum.models.FoodResponse
import com.warriorminds.lifesum.network.FoodService
import com.warriorminds.lifesum.repositories.Locale
import com.warriorminds.lifesum.repositories.SearchRepositoryImpl
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.mockito.MockitoAnnotations
import retrofit2.Response
import retrofit2.mock.Calls
import java.io.File

class SearchRepositoryTest {

    @Mock
    lateinit var service: FoodService
    @Mock
    lateinit var locale: Locale
    lateinit var repository: SearchRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = SearchRepositoryImpl(service, locale)
        `when`(locale.getCountry()).thenReturn("us")
        `when`(locale.getLanguage()).thenReturn("en")
    }

    @Test
    fun searchFoodAsyncTest() {
        val response = Gson().fromJson(getJson("json/response.json", this.javaClass.classLoader), FoodResponse::class.java)
        `when`(service.searchFood(anyString(), anyString(), anyString())).thenReturn(CompletableDeferred(Response.success(200, response)))
        runBlocking {
            val result = repository.searchFoodAsync("test")
            assertEquals(21, result!!.size)
            assertEquals("hamburguer", result[0].title)
        }
    }

    @Test
    fun searchFoodAsyncFailureTest() {
        `when`(service.searchFood(anyString(), anyString(), anyString())).thenReturn(CompletableDeferred(Response.error(500, ResponseBody.create(null, ""))))
        runBlocking {
            val result = repository.searchFoodAsync("test")
            assertNull(result)
        }
    }
}
