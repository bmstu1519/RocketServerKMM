package org.rocketserverkmm.project.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.rocketserverkmm.project.domain.models.LaunchList.LaunchDTO
import org.rocketserverkmm.project.domain.models.LaunchList.LaunchesResult
import org.rocketserverkmm.project.domain.usecases.GetLaunchesUseCase
import org.rocketserverkmm.project.presentation.states.LaunchListAction
import org.rocketserverkmm.project.presentation.states.LaunchListDestination
import org.rocketserverkmm.project.presentation.states.LaunchListState

class LaunchListViewModel(
    private val getLaunchesUseCase: GetLaunchesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LaunchListState())
    val state: StateFlow<LaunchListState> = _state

    private val _destination = MutableSharedFlow<LaunchListDestination>()
    val destination: SharedFlow<LaunchListDestination> = _destination

    private var currentCursor: String? = null

    fun actionToDestination(action: LaunchListAction) {
        when (action) {
            is LaunchListAction.Load -> loadItems()
            is LaunchListAction.LoadMore -> loadItems()
            is LaunchListAction.NavigateToDetails -> navigateToDetails(action.launchId)
        }
    }

    private fun loadItems() {
        if (_state.value.isLoading) return

        viewModelScope.launch {
            updateState(isLoading = true)
            val result = getLaunchesUseCase(currentCursor)
            handleResult(result)
        }
    }

    private fun navigateToDetails(launchId: String) {
        viewModelScope.launch {
            _destination.emit(LaunchListDestination.LaunchDetails(launchId))
        }
    }

    private suspend fun handleResult(result: LaunchesResult) {
        currentCursor = result.cursor
        updateState(
            launches = _state.value.launches + result.launches,
            hasMore = result.hasMore,
            isLoading = false
        )
    }

    private suspend fun updateState(
        launches: List<LaunchDTO>? = null,
        hasMore: Boolean? = null,
        isLoading: Boolean? = null,
        error: String? = null
    ) {
        _state.update { current ->
            current.copy(
                launches = launches ?: current.launches,
                hasMore = hasMore ?: current.hasMore,
                isLoading = isLoading ?: current.isLoading,
                error = error
            )
        }
    }
}

