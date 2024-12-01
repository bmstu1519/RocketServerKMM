package org.rocketserverkmm.project

import androidx.compose.ui.window.ComposeUIViewController
import org.rocketserverkmm.project.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}