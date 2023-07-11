package dev.arunkumar.dagger.spi.validation.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dev.arunkumar.dagger.spi.validation.MainActivity
import dev.arunkumar.dagger.spi.validation.SpiValidation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import javax.inject.Singleton

@Module
object AppModule {
    @Provides
    @Singleton
    fun globalScope(): CoroutineScope = GlobalScope
}

@Singleton
@Component(
    modules = [
        AppModule::class,
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