package dev.arunkumar.dagger.validator

import dagger.Module
import dagger.Provides
import dagger.model.BindingGraph
import dagger.multibindings.ElementsIntoSet
import dagger.spi.DiagnosticReporter
import javax.inject.Inject
import javax.tools.Diagnostic.Kind.ERROR
import javax.tools.Diagnostic.Kind.WARNING

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
     * failures/concerns to `diagnosticReporter`.
     */
    fun validate()
}

@Module
object ValidationModule {
    @Provides
    @ElementsIntoSet
    @JvmSuppressWildcards
    fun validations(
        androidContextValidation: AndroidContextValidation,
        primitivesValidation: PrimitivesValidation,
        globalCoroutineScopeValidation: GlobalCoroutineScopeValidation
    ): Set<Validation> = setOf(
        androidContextValidation,
        primitivesValidation,
        globalCoroutineScopeValidation
    )
}

class AndroidContextValidation
@Inject
constructor(
    override val bindingGraph: BindingGraph,
    override val diagnosticReporter: DiagnosticReporter
) : Validation {
    override fun validate() {
        bindingGraph.bindings()
            .filter { binding ->
                val key = binding.key()
                key.type().toString() == "android.content.Context" && !key.qualifier().isPresent
            }.forEach { contextBinding ->
                diagnosticReporter.reportBinding(
                    WARNING,
                    contextBinding,
                    "Please annotate context binding with any qualifier"
                )
            }
    }
}


class PrimitivesValidation
@Inject
constructor(
    override val bindingGraph: BindingGraph,
    override val diagnosticReporter: DiagnosticReporter
) : Validation {
    override fun validate() {
        bindingGraph.bindings()
            .filter { binding ->
                val key = binding.key()
                key.type().toString() == Integer::class.java.name
                        && !key.qualifier().isPresent
            }.forEach { binding ->
                diagnosticReporter.reportBinding(
                    WARNING,
                    binding,
                    "Primitives should be annotated with any qualifier"
                )
            }
    }
}

class GlobalCoroutineScopeValidation
@Inject
constructor(
    override val bindingGraph: BindingGraph,
    override val diagnosticReporter: DiagnosticReporter
) : Validation {
    override fun validate() {
        bindingGraph.bindings()
            .filter { binding ->
                val scope = binding.scope()
                val isActivityScopedBinding = scope.isPresent && scope.get().scopeAnnotation()
                    .annotationType.toString() == "dev.arunkumar.dagger.spi.validation.di.ActivityScope"
                val hasGlobalCoroutineScope = bindingGraph
                    .requestedBindings(binding)
                    .any { binding ->
                        val isCoroutineScope = binding.key().type().toString() == "kotlinx.coroutines.CoroutineScope"
                        val fromAppComponent = binding
                            .componentPath().toString() == "dev.arunkumar.dagger.spi.validation.di.AppComponent"
                        isCoroutineScope && fromAppComponent
                    }
                isActivityScopedBinding && hasGlobalCoroutineScope
            }.forEach { binding ->
                diagnosticReporter.reportBinding(
                    ERROR,
                    binding,
                    "Activity scoped bindings must not use global coroutine scope"
                )
            }
    }
}