package org.rocketserverkmm.project.domain.models.launchList

data class LaunchesResult(
    val launches: List<Launch>,
    val hasMore: Boolean,
    val cursor: String?
)