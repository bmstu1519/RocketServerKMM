package org.rocketserverkmm.project.presentation.states

import org.rocketserverkmm.project.domain.models.LaunchDTO

data class LaunchListState(
    val launches: List<LaunchDTO> = emptyList(),
    val isLoading: Boolean = false,
    val hasMore: Boolean = false,
    val error: String? = null
)