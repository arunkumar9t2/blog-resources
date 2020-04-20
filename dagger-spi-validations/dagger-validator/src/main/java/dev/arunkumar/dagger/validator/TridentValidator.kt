package dev.arunkumar.dagger.validator

import com.google.auto.service.AutoService
import dagger.model.BindingGraph
import dagger.spi.BindingGraphPlugin
import dagger.spi.DiagnosticReporter
import dev.arunkumar.dagger.validator.di.DaggerTridentComponent
import dev.arunkumar.dagger.validator.di.TridentModule
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

@AutoService(BindingGraphPlugin::class)
class TridentValidator : BindingGraphPlugin {
    private lateinit var types: Types
    private lateinit var elements: Elements
    private lateinit var options: Map<String, String>

    override fun initTypes(types: Types) {
        this.types = types
    }

    override fun initElements(elements: Elements) {
        this.elements = elements
    }

    override fun initOptions(options: MutableMap<String, String>) {
        this.options = options
    }

    override fun supportedOptions() = SUPPORTED_OPTIONS

    override fun visitGraph(
        bindingGraph: BindingGraph,
        diagnosticReporter: DiagnosticReporter
    ) {
        val tridentModule = TridentModule(types, elements, parseOptions(options))
        DaggerTridentComponent.factory()
            .create(tridentModule, bindingGraph, diagnosticReporter)
            .validator()
            .doValidation()
    }
}
