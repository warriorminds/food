package com.warriorminds.lifesum.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.warriorminds.lifesum.R
import com.warriorminds.lifesum.models.Food
import com.warriorminds.lifesum.utils.FOOD_PARAM
import com.warriorminds.lifesum.utils.setValuesOrHide
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    private var food: Food? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        food = arguments?.getSerializable(FOOD_PARAM) as Food

        nutrition_calories.setValuesOrHide(nutrition_calories_value, food?.calories?.toString())
        nutrition_protein.setValuesOrHide(nutrition_protein_value, food?.protein?.toString())
        nutrition_fiber.setValuesOrHide(nutrition_fiber_value, food?.fiber?.toString())
        nutrition_sugars.setValuesOrHide(nutrition_sugars_value, food?.sugar?.toString())
        nutrition_saturated_fat.setValuesOrHide(nutrition_saturated_fat_value, food?.saturatedFat?.toString())
        nutrition_unsaturated_fat.setValuesOrHide(nutrition_unsaturated_fat_value, food?.unsaturatedFat?.toString())
        nutrition_cholesterol.setValuesOrHide(nutrition_cholesterol_value, food?.cholesterol?.toString())
        nutrition_sodium.setValuesOrHide(nutrition_sodium_value, food?.sodium?.toString())
        nutrition_potassium.setValuesOrHide(nutrition_potassium_value, food?.potassium?.toString())
    }

    override fun onResume() {
        super.onResume()
        activity!!.toolbar.title = food?.title
    }
}
