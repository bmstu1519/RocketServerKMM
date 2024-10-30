package org.rocketserverkmm.project.domain.models

import org.rocketserverkmm.project.LaunchListQuery

data class LaunchDTO(
    val id: String,
    val missionName: String?,
    val site: String?,
    val missionPatchUrl: String?
)

fun LaunchListQuery.Launch.toDomain(): LaunchDTO {
    return LaunchDTO(
        id = this.id,
        missionName = this.mission?.name,
        site = this.site,
        missionPatchUrl = this.mission?.missionPatch,
    )
}