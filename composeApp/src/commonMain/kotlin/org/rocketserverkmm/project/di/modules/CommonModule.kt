package org.rocketserverkmm.project.di.modules

import org.koin.dsl.module
import org.rocketserverkmm.project.settings.local.UserConfigHolder

val commonModule = module {
    single {
        UserConfigHolder()
    }
}