package com.warriorminds.lifesum.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FoodResponse(val food: List<Food>)

@Entity(indices = [(Index("id", unique = true))])
data class Food(
    @PrimaryKey
    val id: Int,
    val measurementId: Int?,
    val servingCategory: Int?,
    @SerializedName("pcstext") val name: String?,
    val fiber: Float?,
    val potassium: Float?,
    @SerializedName("saturatedfat") val saturatedFat: Float?,
    val fat: Float?,
    val title: String?,
    val calories: Int?,
    @SerializedName("gramsperserving") val gramsPerServing: Float?,
    val cholesterol: Float?,
    val carbohydrates: Float?,
    val protein: Float?,
    val sodium: Float?,
    val category: String?,
    @SerializedName("unsaturatedfat") val unsaturatedFat: Float?,
    val sugar: Float?,
    val brand: String?
) : Serializable