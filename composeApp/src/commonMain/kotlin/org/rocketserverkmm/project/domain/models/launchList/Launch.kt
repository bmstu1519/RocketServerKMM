package org.rocketserverkmm.project.domain.models.launchList

data class Launch(
    val id: String,
    val missionName: String?,
    val site: String?,
    val missionPatchUrl: String?
)