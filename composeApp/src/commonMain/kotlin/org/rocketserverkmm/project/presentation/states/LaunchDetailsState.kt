package org.rocketserverkmm.project.presentation.states

import org.rocketserverkmm.project.domain.models.launchDetails.MissionDTO
import org.rocketserverkmm.project.domain.models.launchDetails.RocketDTO
import org.rocketserverkmm.project.domain.models.launchDetails._LaunchDetailsState

data class LaunchDetailsState(
    val bookedState: ButtonState? = null,
    val isBooked: Boolean? = null,
    val mission: MissionDTO? = null,
    val rocket: RocketDTO? = null,
    val errorMessage: String? = null,
)
//{
//    companion object{
//        operator fun _LaunchDetailsState.invoke() : LaunchDetailsState {
//            return LaunchDetailsState(
//                bookedState = this.bookedState,
//                isBooked = this.isBooked,
//                mission = this.mission,
//                rocket = this.rocket,
//                errorMessage = this.errorMessage,
//            )
//        }
//    }
//}

sealed class LaunchDetailsAction {
    data class Load(val launchId: String): LaunchDetailsAction()
    data object ClickBookButton: LaunchDetailsAction()
}

sealed class LaunchDetailsDestination {
    data object GoToLogin: LaunchDetailsDestination()
    data object Booked: LaunchDetailsDestination()
}