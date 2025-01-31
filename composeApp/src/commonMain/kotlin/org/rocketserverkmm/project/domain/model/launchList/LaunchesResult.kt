package org.rocketserverkmm.project.domain.model.launchList

data class LaunchesResult(
    val launches: List<Launch>,
    val hasMore: Boolean,
    val cursor: String?
)