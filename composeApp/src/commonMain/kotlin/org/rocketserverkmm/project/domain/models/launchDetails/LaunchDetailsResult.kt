package org.rocketserverkmm.project.domain.models.launchDetails

import org.rocketserverkmm.project.LaunchDetailsQuery

data class LaunchDetailsResult(
    val id: String? = null,
    val site: String? = null,
    val mission: MissionDTO? = null,
    val rocket: RocketDTO? = null,
    val isBooked: Boolean? = null,
)

data class MissionDTO(
    val name: String?,
    val missionPatch: String?,
) {
    companion object {
        operator fun invoke(mission:  LaunchDetailsQuery.Mission?): MissionDTO = MissionDTO(
            name = mission?.name,
            missionPatch = mission?.missionPatch,
        )
    }
}

class RocketDTO(
    val name: String?,
    val type: String?,
) {
    companion object {
        operator fun invoke(rocket:  LaunchDetailsQuery.Rocket?): RocketDTO = RocketDTO(
            name = rocket?.name,
            type = rocket?.type,
        )
    }
}