package org.rocketserverkmm.project.presentation.states

import org.rocketserverkmm.project.domain.models.launchList.LaunchDTO

data class LaunchListState(
    val launches: List<LaunchDTO> = emptyList(),
    val isLoading: Boolean = false,
    val hasMore: Boolean = false,
    val error: String? = null
)

sealed class LaunchListAction {
    data object Load : LaunchListAction()
    data object LoadMore : LaunchListAction()
    data class NavigateToDetails(val launchId: String) : LaunchListAction()
}

sealed class LaunchListDestination {
    data class LaunchDetails(val launchId: String) : LaunchListDestination()
}