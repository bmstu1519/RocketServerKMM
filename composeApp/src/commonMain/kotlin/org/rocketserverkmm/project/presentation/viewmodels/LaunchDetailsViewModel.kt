package org.rocketserverkmm.project.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.rocketserverkmm.project.domain.models.launchDetails.MissionDTO
import org.rocketserverkmm.project.domain.models.launchDetails.RocketDTO
import org.rocketserverkmm.project.domain.models.launchDetails._LaunchDetailsState
import org.rocketserverkmm.project.domain.usecases.GetLaunchDetailsUseCase
import org.rocketserverkmm.project.presentation.states.ButtonState
import org.rocketserverkmm.project.presentation.states.LaunchDetailsAction
import org.rocketserverkmm.project.presentation.states.LaunchDetailsDestination
import org.rocketserverkmm.project.presentation.states.LaunchDetailsState

class LaunchDetailsViewModel(
    private val getLaunchDetailsUseCase: GetLaunchDetailsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(LaunchDetailsState())
    val state: StateFlow<LaunchDetailsState> = _state

    private val _destination = MutableSharedFlow<LaunchDetailsDestination>()
    val destination: SharedFlow<LaunchDetailsDestination> = _destination

    private var _launchId: String = ""

    fun actionToDestination(action: LaunchDetailsAction) {
        when (action) {
            is LaunchDetailsAction.Load -> load(action.launchId)
            LaunchDetailsAction.ClickBookButton -> clickButton()
        }
    }

    private fun load(launchId: String) {
        viewModelScope.launch {
            _launchId = launchId
            val result = getLaunchDetailsUseCase.getLaunchDetails(launchId)
            result
                .onSuccess { success ->
                    handleResult(
                        _LaunchDetailsState(
                            isBooked = success.isBooked,
                            mission = success.mission,
                            rocket = success.rocket,
                        )
                    )
                }
                .onFailure { failed ->
                    handleResult(
                        _LaunchDetailsState(
                            errorMessage = failed.message
                        )
                    )
                }
        }
    }

    private fun clickButton() {
        viewModelScope.launch {
            val isBooked = state.value.isBooked
            isBooked?.let {
                val result = getLaunchDetailsUseCase.tripMutation(_launchId, isBooked)
                result
                    .onSuccess {
                        handleResult(
                            _LaunchDetailsState(
                                isBooked = !isBooked
                            )
                        )
                    }
                    .onFailure { exception ->
                        println("LaunchDetails: Failed to book/cancel trip ${exception.message}")
                        handleResult(
                            _LaunchDetailsState(
                                bookedState = ButtonState.Error,
                                errorMessage = exception.message
                            )
                        )
                    }
            }
        }
    }

    private suspend fun handleResult(result: _LaunchDetailsState) {
        updateState(
            bookedState = result.bookedState,
            isBooked = result.isBooked,
            mission = result.mission,
            rocket = result.rocket,
            errorMessage = result.errorMessage,
        )
    }

    private fun updateState(
        bookedState: ButtonState? = null,
        isBooked: Boolean? = null,
        mission: MissionDTO? = null,
        rocket: RocketDTO? = null,
        errorMessage: String? = null,
    ) {
        _state.update { current ->
            current.copy(
                bookedState = bookedState ?: current.bookedState,
                isBooked = isBooked ?: current.isBooked,
                mission = mission ?: current.mission,
                rocket = rocket ?: current.rocket,
                errorMessage = errorMessage ?: current.errorMessage,
            )
        }
    }

}