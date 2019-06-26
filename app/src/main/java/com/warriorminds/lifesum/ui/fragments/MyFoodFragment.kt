package com.warriorminds.lifesum.ui.fragments


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.warriorminds.lifesum.R
import com.warriorminds.lifesum.adapters.FoodAdapter
import com.warriorminds.lifesum.adapters.SwipeToDeleteCallback
import com.warriorminds.lifesum.utils.hide
import com.warriorminds.lifesum.viewmodels.MyFoodViewModel
import com.warriorminds.lifesum.viewmodels.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_food.*
import javax.inject.Inject

class MyFoodFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var myFoodAdapter: FoodAdapter
    lateinit var viewModel: MyFoodViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[MyFoodViewModel::class.java]
        viewModel.myFood.observe(this, Observer {
            food_progress.hide()
            myFoodAdapter.setFoodList(it)
        })
        viewModel.error.observe(this, Observer {
            food_progress.hide()
            Snackbar.make(food_list, getString(R.string.saved_food_error), Snackbar.LENGTH_LONG).show()
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(food_list) {
            layoutManager = LinearLayoutManager(context)
            adapter = myFoodAdapter
        }

        val swipe = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = myFoodAdapter.getFoodItem(position)
                item?.let { foodItem ->
                    Snackbar.make(food_list, getString(R.string.food_deleted), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.undo)) {
                            myFoodAdapter.addFood(foodItem, position)
                            viewModel.saveFood(foodItem)
                        }
                        .show()
                    viewModel.deleteFood(foodItem)
                    myFoodAdapter.removeAt(position)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(food_list)
        viewModel.fetchMyFood()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_main, menu)
    }
}
