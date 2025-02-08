package org.rocketserverkmm.project.presentation.states

import kotlinx.coroutines.delay

sealed class ButtonState {
    abstract suspend fun handleStateChange(delay: Long?, resetButtonState: () -> Unit)

    data object Loading: ButtonState() {
        override suspend fun handleStateChange(delay: Long?, resetButtonState: () -> Unit) {
            TODO("Not yet implemented")
        }
    }

    data object Success: ButtonState() {
        override suspend fun handleStateChange(delay: Long?, resetButtonState: () -> Unit) {
            val timer = delay ?: 0L
            timer.takeIf { it > 0L }?.let {
                delay(it)
            }
            resetButtonState()
        }
    }

    data object Error: ButtonState() {
        override suspend fun handleStateChange(delay: Long?, resetButtonState: () -> Unit) {
            val timer = delay ?: 0L
            timer.takeIf { it > 0L }?.let {
                delay(it)
            }
            resetButtonState()
        }
    }
}