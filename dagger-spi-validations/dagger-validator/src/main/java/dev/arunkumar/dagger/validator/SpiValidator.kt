package dev.arunkumar.dagger.validator

import com.google.auto.service.AutoService
import dagger.model.BindingGraph
import dagger.spi.BindingGraphPlugin
import dagger.spi.DiagnosticReporter

@AutoService(BindingGraphPlugin::class)
class SpiValidator : BindingGraphPlugin {
    override fun visitGraph(bindingGraph: BindingGraph, diagnosticReporter: DiagnosticReporter) {
    }
}
