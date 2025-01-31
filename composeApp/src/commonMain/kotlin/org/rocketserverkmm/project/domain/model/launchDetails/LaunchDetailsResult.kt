package org.rocketserverkmm.project.domain.model.launchDetails

data class LaunchDetailsResult(
    val id: String? = null,
    val site: String? = null,
    val mission: Mission? = null,
    val rocket: Rocket? = null,
    val isBooked: Boolean? = null,
)