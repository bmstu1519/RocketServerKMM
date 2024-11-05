package org.rocketserverkmm.project.presentation.states

import org.rocketserverkmm.project.domain.models.launchDetails.MissionDTO
import org.rocketserverkmm.project.domain.models.launchDetails.RocketDTO

data class LaunchDetailsState(
    val isLoading: Boolean? = null,
    val bookedState: ButtonState? = null,
    val isBooked: Boolean? = null,
    val mission: MissionDTO? = null,
    val rocket: RocketDTO? = null,
    val site: String? = null,
    val errorMessage: String? = null,
)

sealed class LaunchDetailsAction {
    data class Load(val launchId: String): LaunchDetailsAction()
    data object ClickBookButton: LaunchDetailsAction()
}

sealed class LaunchDetailsDestination {
    data object GoToLogin: LaunchDetailsDestination()
    data object Booked: LaunchDetailsDestination()
}