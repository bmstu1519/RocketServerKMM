package org.rocketserverkmm.project.domain.usecases

import org.rocketserverkmm.project.domain.repositories.AppBootstrapRepository
import org.rocketserverkmm.project.platform.KEY_TOKEN
import org.rocketserverkmm.project.presentation.states.UserAuthState
import org.rocketserverkmm.project.presentation.viewmodels.Constants.THEME_KEY

class GetAppBootstrapUseCase(
    private val appBootstrapRepository: AppBootstrapRepository
) {
    suspend fun getUserAuth(key: String = KEY_TOKEN) : UserAuthState = appBootstrapRepository.getUserAuth(key)
    suspend fun getCurrentTheme(key: String = THEME_KEY ) : Boolean = appBootstrapRepository.getCurrentTheme(key)
}