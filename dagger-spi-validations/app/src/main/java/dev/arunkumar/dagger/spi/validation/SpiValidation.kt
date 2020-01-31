package dev.arunkumar.dagger.spi.validation

import dagger.android.support.DaggerApplication
import dev.arunkumar.dagger.spi.validation.di.DaggerAppComponent

class SpiValidation : DaggerApplication() {
    val appComponent by lazy { DaggerAppComponent.factory().create(this) }

    override fun applicationInjector() = appComponent
}