package dev.arunkumar.dagger.validator

import dev.arunkumar.dagger.validator.SupportedOptions.ENABLED
import dev.arunkumar.dagger.validator.SupportedOptions.values

/**
 * Name space for compiler options
 */
private const val TRIDENT_NAMESPACE = "trident"

/**
 * Source of truth for all supported options
 */
enum class SupportedOptions(val key: String) {
    ENABLED("$TRIDENT_NAMESPACE.enabled"),
}

val SUPPORTED_OPTIONS = values().map { it.key }.toMutableSet()

data class TridentOptions(val enabled: Boolean = false)

/**
 * @return true if this map contains `key` and its value is a `Boolean`.
 */
private fun Map<String, String>.booleanValue(key: String): Boolean {
    return containsKey(key) && get(key)?.toBoolean() == true
}

/**
 * Parses raw key value pair received from javac and maps it to typed data structure (`TridentOptions`)
 */
fun Map<String, String>.asTridentOptions(): TridentOptions {
    val enabled = booleanValue(ENABLED.key)
    return TridentOptions(enabled)
}