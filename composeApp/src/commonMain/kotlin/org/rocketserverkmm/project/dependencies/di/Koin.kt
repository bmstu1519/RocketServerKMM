package org.rocketserverkmm.project.dependencies.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.rocketserverkmm.project.dependencies.di.modules.clientsModule
import org.rocketserverkmm.project.dependencies.di.modules.viewModelsModule

fun initKoin(config: KoinAppDeclaration? = {}) =
    startKoin {
        config?.invoke(this)
        modules(clientsModule, viewModelsModule)
    }