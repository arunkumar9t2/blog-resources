package dev.arunkumar.dagger.validator.di

import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.model.BindingGraph
import dagger.spi.DiagnosticReporter
import dev.arunkumar.dagger.validator.TridentOptions
import dev.arunkumar.dagger.validator.ValidationModule
import dev.arunkumar.dagger.validator.Validator
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

@Component(
    modules = [
        TridentModule::class,
        ValidationModule::class
    ]
)
interface TridentComponent {

    fun validator(): Validator

    @Component.Factory
    interface Factory {
        fun create(
            tridentModule: TridentModule,
            @BindsInstance bindingGraph: BindingGraph,
            @BindsInstance diagnosticReporter: DiagnosticReporter
        ): TridentComponent
    }
}

@Module
class TridentModule(
    private val types: Types,
    private val elements: Elements,
    private val options: TridentOptions
) {
    @Provides
    fun types() = types

    @Provides
    fun elements() = elements

    @Provides
    fun options() = options
}