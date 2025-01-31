package org.rocketserverkmm.project.domain.model.launchList

data class Launch(
    val id: String,
    val missionName: String?,
    val site: String?,
    val missionPatchUrl: String?
)