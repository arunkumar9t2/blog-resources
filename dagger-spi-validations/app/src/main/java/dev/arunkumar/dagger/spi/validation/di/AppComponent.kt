package dev.arunkumar.dagger.spi.validation.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dev.arunkumar.dagger.spi.validation.MainActivity
import dev.arunkumar.dagger.spi.validation.SpiValidation
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MainActivity.Builder::class,
        AndroidSupportInjectionModule::class
    ]
)
interface AppComponent : AndroidInjector<SpiValidation> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}