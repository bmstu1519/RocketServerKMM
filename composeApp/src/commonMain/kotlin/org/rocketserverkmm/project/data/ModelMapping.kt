package org.rocketserverkmm.project.data

import org.rocketserverkmm.project.LaunchDetailsQuery
import org.rocketserverkmm.project.LaunchListQuery
import org.rocketserverkmm.project.domain.models.launchDetails.Mission
import org.rocketserverkmm.project.domain.models.launchDetails.Rocket
import org.rocketserverkmm.project.domain.models.launchList.Launch

fun LaunchListQuery.Launch.toDomain(): Launch {
    return Launch(
        id = this.id,
        missionName = this.mission?.name,
        site = this.site,
        missionPatchUrl = this.mission?.missionPatch,
    )
}

fun LaunchDetailsQuery.Mission.toDomain() : Mission {
    return Mission(
        name = this.name,
        missionPatch = this.missionPatch,
    )
}

fun LaunchDetailsQuery.Rocket.toDomain() : Rocket {
    return Rocket(
        name = this.name,
        type = this.type,
    )
}