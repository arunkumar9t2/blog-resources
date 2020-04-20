package dev.arunkumar.dagger.validator

import javax.inject.Inject

class Validator
@Inject
constructor(
    private val tridentOptions: TridentOptions,
    private val validations: Set<@JvmSuppressWildcards Validation>
) {
    fun doValidation() {
        if (tridentOptions.enabled) {
            validations.forEach { validation ->
                validation.validate()
            }
        }
    }
}