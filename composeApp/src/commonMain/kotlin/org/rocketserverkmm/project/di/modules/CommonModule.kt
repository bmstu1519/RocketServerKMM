package org.rocketserverkmm.project.di.modules

import org.koin.dsl.module
import org.rocketserverkmm.project.data.local.UserConfigHolder

val commonModule = module {
    single {
        UserConfigHolder()
    }
}