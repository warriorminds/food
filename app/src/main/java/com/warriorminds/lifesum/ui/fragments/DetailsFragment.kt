package com.warriorminds.lifesum.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.warriorminds.lifesum.R
import com.warriorminds.lifesum.models.Food
import com.warriorminds.lifesum.utils.FOOD_PARAM
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.activity_main.*

class DetailsFragment : Fragment() {

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
        val food = arguments?.getSerializable(FOOD_PARAM) as Food
        activity!!.toolbar.title = food.title
    }
}
