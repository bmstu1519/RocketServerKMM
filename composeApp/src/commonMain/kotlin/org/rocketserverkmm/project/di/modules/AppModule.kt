package org.rocketserverkmm.project.di.modules

import org.koin.dsl.module

val appModule = module {
    includes(
        repositoriesModule,
        useCasesModule,
        viewModelsModule,
        commonModule
    )
}