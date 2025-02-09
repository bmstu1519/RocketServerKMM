package org.rocketserverkmm.project.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.rocketserverkmm.project.di.modules.appModule
import org.rocketserverkmm.project.di.modules.clientsModule
import org.rocketserverkmm.project.di.modules.scopeModule

fun initKoin(config: KoinAppDeclaration? = {}) =
    startKoin {
        config?.invoke(this)
        modules(clientsModule, appModule, scopeModule)
    }