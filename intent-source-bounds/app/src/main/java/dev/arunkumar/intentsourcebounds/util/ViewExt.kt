package dev.arunkumar.intentsourcebounds.util

import android.animation.Animator
import android.graphics.Rect
import android.view.View
import android.view.ViewAnimationUtils
import androidx.core.view.ViewCompat
import androidx.core.view.doOnNextLayout

/**
 * Computes a [Rect] defining the location of this [View] in terms of screen coordinates
 *
 * Note: The view must be laid out before calling this else the returned [Rect] might not be valid
 */
private fun View.computeScreenBounds(): Rect {
    val viewLocation = IntArray(2).apply { getLocationOnScreen(this) }
    val contentX = viewLocation[0]
    val contentY = viewLocation[1]
    return Rect(
        contentX,
        contentY,
        contentX + width,
        contentY + height
    )
}

/**
 * Computes a [Rect] defining the location of this [View] and invokes [action] with the computed bounds when available
 */
fun View.screenBounds(action: (Rect) -> Unit) {
    if (!ViewCompat.isLaidOut(this) && !isLayoutRequested) {
        action(computeScreenBounds())
    } else {
        doOnNextLayout {
            action(computeScreenBounds())
        }
    }
}

/**
 * @see ViewAnimationUtils.createCircularReveal
 */
fun View.createCircularReveal(centerX: Int, centerY: Int, startRadius: Float, endRadius: Float): Animator {
    return ViewAnimationUtils.createCircularReveal(this, centerX, centerY, startRadius, endRadius)
}