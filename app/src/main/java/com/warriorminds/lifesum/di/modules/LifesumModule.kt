package com.warriorminds.lifesum.di.modules

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.warriorminds.lifesum.db.FoodDatabase
import com.warriorminds.lifesum.network.FoodService
import com.warriorminds.lifesum.repositories.*
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class LifesumModule(private val context: Context) {

    @Provides
    @Singleton
    fun providesContext(): Context = context

    @Provides
    @Singleton
    fun providesMyFoodRepository(myFoodRepository: MyFoodRepositoryImpl): MyFoodRepository = myFoodRepository

    @Provides
    @Singleton
    fun providesSearchRepository(searchRepository: SearchRepositoryImpl): SearchRepository = searchRepository

    @Singleton
    @Provides
    fun providesOkHttp() = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .cache(Cache(File(context.cacheDir, "http-cache"), 25 * 1024 * 1024))
        .build()

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .baseUrl("https://api.lifesum.com/")
        .build()

    @Singleton
    @Provides
    fun providesFoodService(retrofit: Retrofit): FoodService = retrofit.create(FoodService::class.java)

    @Singleton
    @Provides
    fun providesDatabase(context: Context): FoodDatabase =
        Room.databaseBuilder(context, FoodDatabase::class.java, "food.db")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun providesLocale(locale: LocaleImpl): Locale = locale
}