package com.warriorminds.lifesum

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.warriorminds.lifesum.di.components.DaggerAppComponent
import com.warriorminds.lifesum.di.modules.LifesumModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class LifesumApp : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()
        val appComponent = DaggerAppComponent.builder()
            .lifesumModule(LifesumModule(this))
            .build()
        appComponent.inject(this)
    }

    override fun activityInjector() = dispatchingActivityInjector

    override fun supportFragmentInjector() = dispatchingFragmentInjector
}