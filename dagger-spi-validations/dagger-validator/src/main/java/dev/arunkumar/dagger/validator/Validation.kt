package dev.arunkumar.dagger.validator

import dagger.Module
import dagger.Provides
import dagger.model.BindingGraph
import dagger.multibindings.ElementsIntoSet
import dagger.spi.DiagnosticReporter
import javax.inject.Inject
import javax.tools.Diagnostic

/**
 * Marker interface to annotate that a class performs validation.
 */
interface Validation {
    /**
     * The [BindingGraph] of the component for which the validation is to be performed
     */
    val bindingGraph: BindingGraph

    /**
     * The [diagnosticReporter] instance with which can be used to report errors/warning to dagger.
     */
    val diagnosticReporter: DiagnosticReporter

    /**
     * Implementation of the method is expected to utilize `bindingGraph` and report any validation
     * failures to `diagnosticReporter`
     */
    fun validate()
}

@Module
object ValidationModule {
    @Provides
    @ElementsIntoSet
    @JvmSuppressWildcards
    fun validations(androidContextValidation: AndroidContextValidation): Set<Validation> {
        return setOf(androidContextValidation)
    }
}

class AndroidContextValidation
@Inject
constructor(
    override val bindingGraph: BindingGraph,
    override val diagnosticReporter: DiagnosticReporter
) : Validation {
    override fun validate() {
        diagnosticReporter.reportBinding(
            Diagnostic.Kind.WARNING,
            bindingGraph.bindings().first(),
            "Test report"
        )
    }
}