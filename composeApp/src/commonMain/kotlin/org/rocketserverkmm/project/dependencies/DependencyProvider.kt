package org.rocketserverkmm.project.dependencies

import org.rocketserverkmm.project.settings.local.KVaultClientProviderSingleton
import org.rocketserverkmm.project.settings.remote.ProvideApolloClientSingleton
import org.rocketserverkmm.project.settings.remote.ProvideKtorClientSingleton
import org.rocketserverkmm.project.repositories.KeyVaultRepositoryImpl
import org.rocketserverkmm.project.repositories.LaunchRepositoryImpl
import org.rocketserverkmm.project.domain.repositories.KeyVaultRepository
import org.rocketserverkmm.project.domain.repositories.LaunchRepository
import org.rocketserverkmm.project.domain.usecases.GetLaunchDetailsUseCase
import org.rocketserverkmm.project.domain.usecases.GetLaunchesUseCase
import org.rocketserverkmm.project.domain.usecases.GetLoginUseCase

object DependencyProvider {

    //clients(все должно быть private)
    val ktorClient = ProvideKtorClientSingleton.getInstance()
    val apolloClient = ProvideApolloClientSingleton.apolloClient
    private val kVaultClient = KVaultClientProviderSingleton.getInstance()

    //feature repositories
    private fun launchRepository(): LaunchRepository = LaunchRepositoryImpl(apolloClient)

    //useCases
    fun getLaunchUseCase(): GetLaunchesUseCase = GetLaunchesUseCase(launchRepository())

    fun getLoginUseCase(): GetLoginUseCase =
        GetLoginUseCase(launchRepository(), getKeyVaultClient())

    fun getLaunchDetailsUseCase(): GetLaunchDetailsUseCase = GetLaunchDetailsUseCase(
        launchRepository(), getKeyVaultClient()
    )

    //use clients
    fun getKeyVaultClient(): KeyVaultRepository = KeyVaultRepositoryImpl(kVaultClient)

}