package org.rocketserverkmm.project.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.rocketserverkmm.project.domain.models.LaunchDTO
import org.rocketserverkmm.project.domain.models.LaunchesResult
import org.rocketserverkmm.project.domain.usecases.GetLaunchesUseCase
import org.rocketserverkmm.project.presentation.intents.LaunchListIntent
import org.rocketserverkmm.project.presentation.states.LaunchListState

class LaunchListViewModel(
    private val getLaunchesUseCase: GetLaunchesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LaunchListState())
    val state = _state.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<String>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private var currentCursor: String? = null

    fun handleIntent(intent: LaunchListIntent) {
        when (intent) {
            is LaunchListIntent.LoadMore -> loadMore()
            is LaunchListIntent.Refresh -> refresh()
            is LaunchListIntent.NavigateToDetails -> navigateToDetails(intent.launchId)
        }
    }

    private fun loadMore() {
        if (_state.value.isLoading) return

        viewModelScope.launch {
            updateState(isLoading = true)
            handleResult(getLaunchesUseCase(currentCursor))
        }
    }

    private fun refresh() {
        currentCursor = null
        _state.update { it.copy(launches = emptyList(), error = null) }
        loadMore()
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

    private fun navigateToDetails(launchId: String) {
        viewModelScope.launch {
            _navigationEvent.emit(launchId)
        }
    }
}
