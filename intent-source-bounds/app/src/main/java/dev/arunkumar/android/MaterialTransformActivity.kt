package dev.arunkumar.android

import `in`.arunkumarsampath.transitionx.prepareTransition
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnNextLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import dev.arunkumar.android.util.hasSourceBounds
import dev.arunkumar.android.util.preAnimationSetup
import dev.arunkumar.android.util.screenBounds
import dev.arunkumar.android.util.sourceBounds
import kotlinx.android.synthetic.main.activity_material_transform.*

class MaterialTransformActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        preAnimationSetup()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_transform)
        setSupportActionBar(toolbar)
        performMaterialTransform()
    }

    private fun performMaterialTransform() {
        if (!hasSourceBounds) {
            rootContentLayout.isInvisible = false
        } else {
            sourceBounds { sourceBounds ->
                rootContentLayout.run {
                    screenBounds { layoutBounds ->
                        if (layoutBounds.contains(sourceBounds)) {
                            // Save current constraints
                            // Apply source bounds dimensions to target
                            updateLayoutParams<FrameLayout.LayoutParams> {
                                width = sourceBounds.width()
                                height = sourceBounds.height()
                                leftMargin = sourceBounds.left
                                topMargin = sourceBounds.top
                            }
                            isVisible = true
                            // Animate to full parent width
                            post {
                                rootLayout.prepareTransition {
                                    auto {
                                        ease {
                                            standardEasing
                                        }
                                    }
                                }
                                updateLayoutParams<FrameLayout.LayoutParams> {
                                    width = FrameLayout.LayoutParams.MATCH_PARENT
                                    height = FrameLayout.LayoutParams.MATCH_PARENT
                                    leftMargin = 0
                                    topMargin = 0
                                }
                            }
                            doOnNextLayout {

                            }
                        } else {
                            rootContentLayout.isInvisible = false
                        }
                    }
                }
            }
        }
    }
}
