package com.warriorminds.lifesum.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.warriorminds.lifesum.R
import com.warriorminds.lifesum.models.Food
import com.warriorminds.lifesum.utils.FOOD_PARAM
import com.warriorminds.lifesum.utils.hide
import com.warriorminds.lifesum.utils.show
import kotlinx.android.synthetic.main.food_item.view.*
import javax.inject.Inject

class FoodAdapter @Inject constructor(private val context: Context) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private var itemActions: ItemActions? = null
    private var food = mutableListOf<Food>()
    private var showItemOptions = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FoodViewHolder(parent)

    override fun getItemCount() = food.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val item = food[position]
        holder.itemView.food_title.text = item.title
        holder.itemView.food_description.text = item.name

        if (showItemOptions) {
            holder.itemView.food_options.show()
            holder.itemView.food_options.setOnClickListener {
                val menu = PopupMenu(context, holder.itemView.food_options)
                menu.inflate(R.menu.menu_food_item)
                menu.setOnMenuItemClickListener {
                    itemActions?.save(item)
                    false
                }
                menu.show()
            }
        } else {
            holder.itemView.food_options.hide()
        }

        val bundle = Bundle()
        bundle.putSerializable(FOOD_PARAM, item)
        holder.itemView.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.details_action, bundle))
    }

    fun setFoodList(food: List<Food>) {
        this.food = food.toMutableList()
        notifyDataSetChanged()
    }

    fun addFood(foodItem: Food, position: Int) {
        food.add(position, foodItem)
        notifyItemInserted(position)
    }

    fun removeAt(position: Int) {
        food.removeAt(position)
        notifyItemRemoved(position)
    }

    fun setItemActions(listener: ItemActions) {
        this.itemActions = listener
    }

    fun showItemOptions(show: Boolean) {
        this.showItemOptions = show
    }

    fun getFoodItem(position: Int) = if (position < food.size) {
        food[position]
    } else {
        null
    }

    inner class FoodViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.food_item, parent, false)
    )

    interface ItemActions {
        fun save(item: Food)
    }
}