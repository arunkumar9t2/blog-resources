package dev.arunkumar.intentsourcebounds

import `in`.arunkumarsampath.transitionx.prepareTransition
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.*
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.transition.ArcMotion
import dev.arunkumar.intentsourcebounds.util.hasSourceBounds
import dev.arunkumar.intentsourcebounds.util.preAnimationSetup
import dev.arunkumar.intentsourcebounds.util.screenBounds
import dev.arunkumar.intentsourcebounds.util.sourceBounds
import kotlinx.android.synthetic.main.activity_hero_transform.*
import kotlinx.android.synthetic.main.content_hero_transform.*

class HeroTransformActivity : AppCompatActivity() {

    private val defaultHeroConstraints by lazy {
        ConstraintSet().apply {
            clone(this@HeroTransformActivity, R.layout.content_hero_transform)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        preAnimationSetup()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_transform)
        performHeroTransformSetup()
    }

    private fun performHeroTransformSetup() {
        if (!hasSourceBounds) {
            layoutDefaults()
        } else {
            sourceBounds { sourceBounds ->
                heroTransformContentLayout.run {
                    screenBounds { layoutBounds ->
                        if (layoutBounds.contains(sourceBounds)) {
                            heroImg.updateLayoutParams<ConstraintLayout.LayoutParams> {
                                width = sourceBounds.width()
                                height = (sourceBounds.height() * 0.75).toInt()
                            }
                            ConstraintSet().apply {
                                clone(heroTransformContentLayout)
                                setMargin(R.id.heroImg, START, sourceBounds.left)
                                setMargin(R.id.heroImg, TOP, sourceBounds.top)
                                clear(R.id.heroImg, END)
                            }.applyTo(this)
                            heroTitle.isVisible = false
                            heroContent.isVisible = false

                            // Next layout
                            post {
                                heroTransformRootLayout.prepareTransition {
                                    changeColor {
                                        +heroTransformContentLayout
                                    }
                                    slide {
                                        +heroTitle
                                        +heroContent
                                    }
                                    moveResize {
                                        pathMotion = ArcMotion()
                                    }
                                }
                                defaultHeroConstraints.applyTo(heroTransformContentLayout)
                                layoutDefaults()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun layoutDefaults() {
        heroTransformContentLayout.background = ColorDrawable(Color.WHITE)
        heroTitle.isVisible = true
        heroContent.isVisible = true
    }
}