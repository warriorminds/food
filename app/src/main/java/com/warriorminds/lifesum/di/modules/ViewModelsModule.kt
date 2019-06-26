package com.warriorminds.lifesum.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.warriorminds.lifesum.viewmodels.MyFoodViewModel
import com.warriorminds.lifesum.viewmodels.SearchViewModel
import com.warriorminds.lifesum.viewmodels.ViewModelFactory
import com.warriorminds.lifesum.viewmodels.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(MyFoodViewModel::class)
    abstract fun providesMyFoodViewModel(viewModel: MyFoodViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun providesSearchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}