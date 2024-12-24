package org.rocketserverkmm.project.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.rocketserverkmm.project.TripsBookedSubscription
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

    private val _snackbarMessage = MutableSharedFlow<String>()
    val snackbarMessage = _snackbarMessage.asSharedFlow()

    private var _launchId: String = ""

    init {
        updateState(
            launchDetailsState = LaunchDetailsState(
                isLoading = true
            )
        )
        viewModelScope.launch {
            getLaunchDetailsUseCase.tripBookedSubscribe(TripsBookedSubscription())
                .onSuccess { success ->
                    success.collect { message ->
                        _snackbarMessage.emit(message)
                    }
                }
                .onFailure { failed ->
                    handleResult(
                        LaunchDetailsState(
                            errorMessage = failed.message
                        )
                    )
                }
        }
    }

    fun actionToDestination(action: LaunchDetailsAction) {
        when (action) {
            is LaunchDetailsAction.Load -> load(action.launchId)
            LaunchDetailsAction.ClickBookButton -> clickButton()
//            LaunchDetailsAction.GetSubscribe -> subscribeToBookResult()
        }
    }

    private fun load(launchId: String) {
        viewModelScope.launch {
            _launchId = launchId
            val result = getLaunchDetailsUseCase.getLaunchDetails(launchId)
            result
                .onSuccess { success ->
                    handleResult(
                        LaunchDetailsState(
                            isLoading = false,
                            isBooked = success.isBooked,
                            mission = success.mission,
                            rocket = success.rocket,
                            site = success.site
                        )
                    )
                }
                .onFailure { failed ->
                    handleResult(
                        LaunchDetailsState(
                            isLoading = false,
                            errorMessage = failed.message
                        )
                    )
                }
        }
    }

    private fun clickButton() {
        val checkToken = getLaunchDetailsUseCase.checkToken()

        if (checkToken) {
            viewModelScope.launch {
                val isBooked = state.value.isBooked
                isBooked?.let {
                    val result = getLaunchDetailsUseCase.tripMutation(_launchId, isBooked)
                    result
                        .onSuccess {
//                            actionToDestination(LaunchDetailsAction.GetSubscribe)
//                            subscribeToBookResult()
                            handleResult(
                                LaunchDetailsState(
                                    isBooked = !isBooked
                                )
                            )
                        }
                        .onFailure { exception ->
                            println("LaunchDetails: Failed to book/cancel trip ${exception.message}")
                            handleResult(
                                LaunchDetailsState(
                                    buttonState = ButtonState.Error,
                                    errorMessage = exception.message
                                )
                            )
                            ButtonState.Error.handleStateChange(2000L) { resetState() }
                        }
                }
            }
        } else {
            goToLogin()
        }
    }

//    private fun subscribeToBookResult() {
//        viewModelScope.launch {
//            getLaunchDetailsUseCase.tripBookedSubscribe(TripsBookedSubscription())
//                .onSuccess { success ->
//                    success.collect { message ->
//                        handleResult(
//                            LaunchDetailsState(
//                                subscribeSnackbar = message
//                            )
//                        )
//                    }
//                }
//                .onFailure { failed ->
//                    handleResult(
//                        LaunchDetailsState(
//                            errorMessage = failed.message
//                        )
//                    )
//                }
//        }
//    }

    private fun handleResult(result: LaunchDetailsState) {
        updateState(
            launchDetailsState = LaunchDetailsState(
                isLoading = result.isLoading,
                buttonState = result.buttonState,
                isBooked = result.isBooked,
                mission = result.mission,
                rocket = result.rocket,
                site = result.site,
                errorMessage = result.errorMessage,
            )
        )
    }

    private fun goToLogin() {
        viewModelScope.launch {
            _destination.emit(LaunchDetailsDestination.GoToLogin)
        }
    }

    private fun resetState(state: LaunchDetailsState? = null) {
        updateState(
            launchDetailsState = state
        )
    }

    private fun updateState(
        launchDetailsState: LaunchDetailsState? = null,
    ) {
        _state.update { current ->
            current.copy(
                isLoading = launchDetailsState?.isLoading ?: current.isLoading,
                buttonState = launchDetailsState?.buttonState,
                isBooked = launchDetailsState?.isBooked ?: current.isBooked,
                mission = launchDetailsState?.mission ?: current.mission,
                rocket = launchDetailsState?.rocket ?: current.rocket,
                site = launchDetailsState?.site ?: current.site,
                errorMessage = launchDetailsState?.errorMessage ?: current.errorMessage,
            )
        }
    }

}