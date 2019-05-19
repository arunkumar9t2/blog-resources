package dev.arunkumar.android

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import dev.arunkumar.android.util.animatedValue
import dev.arunkumar.android.util.createCircularReveal
import dev.arunkumar.android.util.screenBounds
import kotlinx.android.synthetic.main.activity_circular_reveal.*
import kotlin.math.hypot

class CircularRevealActivity : AppCompatActivity() {
    /**
     * @return `true` if launching [Intent] has [Intent.getSourceBounds] set.
     */
    private val hasSourceBounds get() = intent?.sourceBounds != null

    override fun onCreate(savedInstanceState: Bundle?) {
        preCircularRevealSetup()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circular_reveal)
        setSupportActionBar(toolbar)
        performCircularReveal()
    }

    private fun preCircularRevealSetup() {
        if (hasSourceBounds) {
            overridePendingTransition(0, 0)
        }
    }

    private fun performCircularReveal() {
        if (!hasSourceBounds) {
            rootLayout.isInvisible = false
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
            window.navigationBarColor = Color.BLACK
        } else {
            intent?.sourceBounds?.let { sourceBounds ->
                rootLayout.run {
                    screenBounds { rootLayoutBounds ->
                        // Verify if sourceBounds is valid
                        val revealDuration = 500L
                        if (rootLayoutBounds.contains(sourceBounds.centerX(), sourceBounds.centerY())) {
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

    private val statusBarAnimator: Animator
        get() = ValueAnimator.ofArgb(Color.TRANSPARENT, window.statusBarColor)
            .animatedValue(window::setStatusBarColor)

    private val navigationBarAnimator: Animator
        get() = ValueAnimator.ofArgb(Color.TRANSPARENT, window.navigationBarColor)
            .animatedValue(window::setNavigationBarColor)
}
