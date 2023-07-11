package dev.arunkumar.dagger.spi.validation

import android.content.Context
import android.os.Bundle
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import dev.arunkumar.dagger.spi.validation.di.ActivityScope
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var context: Context

    @Inject
    @JvmField
    var count: Int = 0

    @Inject
    lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @dagger.Module
    object Module {
        @Provides
        fun providesCount(): Int = 20
    }

    @dagger.Module
    interface Builder {
        @ActivityScope
        @ContributesAndroidInjector(modules = [Module::class])
        fun mainActivity(): MainActivity
    }
}