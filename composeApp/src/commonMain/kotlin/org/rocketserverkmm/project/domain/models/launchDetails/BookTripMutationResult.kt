package org.rocketserverkmm.project.domain.models.launchDetails

data class BookTripMutationResult(
    val tripMutation: MutationDto
)

sealed class MutationDto(
    open val success: Boolean,
    open val message: String?,
) {
    data class BookTrip(
        override val success: Boolean,
        override val message: String?,
    ) : MutationDto(success, message)

    data class CancelTrip(
        override val success: Boolean,
        override val message: String?,
    ) : MutationDto(success, message)
}