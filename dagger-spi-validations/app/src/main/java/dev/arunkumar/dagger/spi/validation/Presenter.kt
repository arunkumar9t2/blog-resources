package dev.arunkumar.dagger.spi.validation

import dev.arunkumar.dagger.spi.validation.di.ActivityScope
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

@ActivityScope
class Presenter
@Inject
constructor(
    private val scope: CoroutineScope
)