package dev.arunkumar.intentsourcebounds

import `in`.arunkumarsampath.transitionx.prepareTransition
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import dev.arunkumar.intentsourcebounds.util.hasSourceBounds
import dev.arunkumar.intentsourcebounds.util.preAnimationSetup
import dev.arunkumar.intentsourcebounds.util.screenBounds
import dev.arunkumar.intentsourcebounds.util.sourceBounds
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
                            // Apply source bounds dimensions to target
                            updateLayoutParams<FrameLayout.LayoutParams> {
                                width = sourceBounds.width()
                                height = sourceBounds.height()
                                leftMargin = sourceBounds.left
                                topMargin = sourceBounds.top
                            }
                            isVisible = true
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
                        } else {
                            rootContentLayout.isInvisible = false
                        }
                    }
                }
            }
        }
    }
}
