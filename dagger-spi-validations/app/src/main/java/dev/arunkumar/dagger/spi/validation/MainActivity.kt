package dev.arunkumar.dagger.spi.validation

import android.content.Context
import android.os.Bundle
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @Module
    interface Builder {
        @ContributesAndroidInjector
        fun mainActivity(): MainActivity
    }
}