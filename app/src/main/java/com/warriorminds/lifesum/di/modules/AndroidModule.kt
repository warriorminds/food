package com.warriorminds.lifesum.di.modules

import com.warriorminds.lifesum.ui.fragments.DetailsFragment
import com.warriorminds.lifesum.ui.fragments.MyFoodFragment
import com.warriorminds.lifesum.ui.fragments.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidModule {

    @ContributesAndroidInjector
    abstract fun myFoodFragment(): MyFoodFragment

    @ContributesAndroidInjector
    abstract fun searchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun detailsFragment(): DetailsFragment
}