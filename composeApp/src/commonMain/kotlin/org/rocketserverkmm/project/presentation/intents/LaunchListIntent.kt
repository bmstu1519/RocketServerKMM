package org.rocketserverkmm.project.presentation.intents

sealed interface LaunchListIntent {
    data object LoadMore : LaunchListIntent
    data object Refresh : LaunchListIntent
    data class NavigateToDetails(val launchId: String) : LaunchListIntent
}