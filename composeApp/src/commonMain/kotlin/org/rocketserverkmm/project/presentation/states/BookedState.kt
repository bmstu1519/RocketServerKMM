package org.rocketserverkmm.project.presentation.states

import org.rocketserverkmm.project.domain.models.launchDetails.LaunchDetailsResult

sealed class BookedState {
    data object Loading : BookedState()
    data class Error(val message: String) : BookedState()
    data class Success(val data: LaunchDetailsResult) : BookedState()
}