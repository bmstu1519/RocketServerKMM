package org.rocketserverkmm.project.dependencies

import org.rocketserverkmm.project.data.remote.ProvideApolloClientSingleton
import org.rocketserverkmm.project.data.remote.ProvideKtorClientSingleton
import org.rocketserverkmm.project.data.repositories.LaunchRepositoryImpl
import org.rocketserverkmm.project.domain.repositories.LaunchRepository
import org.rocketserverkmm.project.domain.usecases.GetLaunchesUseCase

object DependencyProvider {

    //clients
    val ktorClient = ProvideKtorClientSingleton.getInstance()
    val apolloClient = ProvideApolloClientSingleton.apolloClient

    //repositories
    private fun getLaunchRepository(): LaunchRepository = LaunchRepositoryImpl(apolloClient)

    //useCases
    fun getLaunchUseCase(): GetLaunchesUseCase = GetLaunchesUseCase(getLaunchRepository())

}