package org.rocketserverkmm.project.domain.repositories

import org.rocketserverkmm.project.presentation.states.UserAuthState

interface AppBootstrapRepository {
    suspend fun getUserAuth(key: String) : UserAuthState
    suspend fun getCurrentTheme(key: String) : Boolean
}