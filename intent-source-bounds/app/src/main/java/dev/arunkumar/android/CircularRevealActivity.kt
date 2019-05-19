package dev.arunkumar.android

import android.animation.AnimatorSet
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import dev.arunkumar.android.util.*
import kotlinx.android.synthetic.main.activity_circular_reveal.*
import kotlin.math.hypot

class CircularRevealActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        preAnimationSetup()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circular_reveal)
        setSupportActionBar(toolbar)
        performCircularReveal()
    }

    private fun performCircularReveal() {
        if (!hasSourceBounds) {
            rootContentLayout.isInvisible = false
        } else {
            sourceBounds { sourceBounds ->
                rootContentLayout.run {
                    screenBounds { rootLayoutBounds ->
                        // Verify if sourceBounds is valid
                        val revealDuration = 500L
                        if (rootLayoutBounds.contains(sourceBounds)) {
                            val circle = createCircularReveal(
                                centerX = sourceBounds.centerX() - rootLayoutBounds.left,
                                centerY = sourceBounds.centerY() - rootLayoutBounds.top,
                                startRadius = (minOf(sourceBounds.width(), sourceBounds.height()) * 0.2).toFloat(),
                                endRadius = hypot(width.toFloat(), height.toFloat())
                            ).apply {
                                isInvisible = false
                                duration = revealDuration
                            }
                            AnimatorSet()
                                .apply { playTogether(circle, statusBarAnimator, navigationBarAnimator) }
                                .start()
                        } else {
                            isInvisible = false
                        }
                    }
                }
            }
        }
    }
}
