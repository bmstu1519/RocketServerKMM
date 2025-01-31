package org.rocketserverkmm.project.presentation.states

import org.rocketserverkmm.project.domain.models.launchDetails.Mission
import org.rocketserverkmm.project.domain.models.launchDetails.Rocket

data class LaunchDetailsState(
    val isLoading: Boolean? = null,
    val buttonState: ButtonState? = null,
    val isBooked: Boolean? = null,
    val mission: Mission? = null,
    val rocket: Rocket? = null,
    val site: String? = null,
    val errorMessage: String? = null,
)

sealed class LaunchDetailsAction {
    data class Load(val launchId: String): LaunchDetailsAction()
    data object ClickBookButton: LaunchDetailsAction()
//    data object GetSubscribe: LaunchDetailsAction()
}

sealed class LaunchDetailsDestination {
    data object GoToLogin: LaunchDetailsDestination()
}