package org.rocketserverkmm.project.dependencies

import org.rocketserverkmm.project.data.remote.ProvideApolloClientSingleton
import org.rocketserverkmm.project.data.remote.ProvideKtorClientSingleton

object DependencyProvider {
    val apolloClient = ProvideApolloClientSingleton.apolloClient
    val ktorClient = ProvideKtorClientSingleton.getInstance()

    //useCase

    //repository

}