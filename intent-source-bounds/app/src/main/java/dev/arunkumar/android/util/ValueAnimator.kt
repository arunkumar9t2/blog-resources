package dev.arunkumar.android.util

import android.animation.ValueAnimator

inline fun <reified T> ValueAnimator.animatedValue(crossinline update: (T) -> Unit): ValueAnimator {
    addUpdateListener { update(it.animatedValue as T) }
    return this
}