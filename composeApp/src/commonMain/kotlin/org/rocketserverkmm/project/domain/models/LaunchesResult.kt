package org.rocketserverkmm.project.domain.models

data class LaunchesResult(
    val launches: List<LaunchDTO>,
    val hasMore: Boolean,
    val cursor: String?
)