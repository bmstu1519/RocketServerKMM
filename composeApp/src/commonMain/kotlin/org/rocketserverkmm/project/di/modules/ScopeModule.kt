package org.rocketserverkmm.project.di.modules

import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.rocketserverkmm.project.di.ScopeManager

val scopeModule = module {
    single { ScopeManager() }

    scope(named("LaunchListScope")) {
        scoped { LaunchListData() }
    }
}