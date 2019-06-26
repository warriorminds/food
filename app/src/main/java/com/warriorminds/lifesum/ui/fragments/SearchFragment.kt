package com.warriorminds.lifesum.ui.fragments


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.warriorminds.lifesum.R
import com.warriorminds.lifesum.adapters.FoodAdapter
import com.warriorminds.lifesum.models.Food
import com.warriorminds.lifesum.utils.hide
import com.warriorminds.lifesum.utils.hideKeyboard
import com.warriorminds.lifesum.utils.show
import com.warriorminds.lifesum.utils.showKeyboard
import com.warriorminds.lifesum.viewmodels.SearchViewModel
import com.warriorminds.lifesum.viewmodels.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_food.*
import javax.inject.Inject


class SearchFragment : Fragment(), FoodAdapter.ItemActions {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var searchAdapter: FoodAdapter
    lateinit var viewModel: SearchViewModel
    private var searchQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[SearchViewModel::class.java]
        viewModel.food.observe(this, Observer {
            food_progress.hide()
            searchAdapter.setFoodList(it)
        })
        viewModel.fetchingError.observe(this, Observer {
            food_progress.hide()
            Snackbar.make(food_list, getString(R.string.error_search), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry)) {
                    food_progress.show()
                    viewModel.searchFood(searchQuery)
                }
                .show()
        })
        viewModel.savingState.observe(this, Observer {
            val message = if (it) {
                getString(R.string.food_save_success)
            } else {
                getString(R.string.error_saving_food)
            }
            Snackbar.make(food_list, message, Snackbar.LENGTH_LONG).show()
        })
        viewModel.noResults.observe(this, Observer {
            food_progress.hide()
            Snackbar.make(food_list, getString(R.string.no_results_found), Snackbar.LENGTH_LONG).show()
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_food, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        food_progress.hide()
        activity!!.toolbar.title = searchQuery

        with(food_list) {
            layoutManager = LinearLayoutManager(context)
            adapter = searchAdapter
        }
        searchAdapter.setItemActions(this)
        searchAdapter.showItemOptions(true)
    }

    override fun save(item: Food) {
        viewModel.saveFood(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_search, menu)
        configureSearchView(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_search -> true
        android.R.id.home -> {
            context?.hideKeyboard(view!!)
            super.onOptionsItemSelected(item)
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun configureSearchView(menu: Menu) {
        val searchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchMenuItem = menu.findItem(R.id.action_search)
        val searchView = searchMenuItem.actionView as SearchView
        searchView.setIconifiedByDefault(false)
        searchView.requestFocus()
        context?.showKeyboard()
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchQuery = query
                    food_progress.show()
                    viewModel.searchFood(searchQuery)
                }
                return false
            }

            override fun onQueryTextChange(query: String) = false
        })
    }
}
