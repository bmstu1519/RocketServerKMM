package org.rocketserverkmm.project.di.modules

import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.rocketserverkmm.project.di.ScopeManager
import org.rocketserverkmm.project.presentation.states.UserAuthState

val scopeModule = module {
    single { ScopeManager() }

    scope(named("FirstLoadDataScope")) {
        scoped { FirstLoadInitialData() }
    }
    scope(named("LaunchListScope")) {
        scoped { LaunchListData() }
    }
}

data class LaunchListData(
    var launchId: String = ""
)

data class FirstLoadInitialData(
    var isUserAuthorized: UserAuthState? = null,
    var isDarkThemeEnabled: Boolean = false
)