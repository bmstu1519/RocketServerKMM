package org.rocketserverkmm.project.presentation.states

import org.rocketserverkmm.project.domain.models.launchDetails.MissionDTO
import org.rocketserverkmm.project.domain.models.launchDetails.RocketDTO

data class LaunchDetailsState(
    val bookedState: ButtonState,
    val isBooked: Boolean,
    val mission: MissionDTO,
    val rocket: RocketDTO,
    val errorMessage: String? = null,
)

sealed class LaunchDetailsAction {
    data object ClickBookButton: LaunchDetailsAction()
    data class BookTrip(val launchId: String): LaunchDetailsAction()
}

sealed class LaunchDetailsDestination {
    data object GoToLogin: LaunchDetailsDestination()
    data object Booked: LaunchDetailsDestination()
}