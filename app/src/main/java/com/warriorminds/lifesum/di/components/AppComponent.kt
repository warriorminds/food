package com.warriorminds.lifesum.di.components

import com.warriorminds.lifesum.LifesumApp
import com.warriorminds.lifesum.di.modules.AndroidModule
import com.warriorminds.lifesum.di.modules.LifesumModule
import com.warriorminds.lifesum.di.modules.ViewModelsModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, LifesumModule::class, AndroidModule::class, ViewModelsModule::class])
interface AppComponent {
    fun inject(app: LifesumApp)
}