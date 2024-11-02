package org.rocketserverkmm.project.domain.models.launchDetails

data class LaunchDetailsResult(
    val id: String,
    val site: String?,
    val mission: MissionDTO?,
    val rocket: RocketDTO?,
    val isBooked: Boolean,
)

data class MissionDTO(
    val name: String?,
    val missionPatch: String?,
)

class RocketDTO(
    val name: String?,
    val type: String?,
)