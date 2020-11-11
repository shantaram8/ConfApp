package kz.kolesateam.confapp.events.data.models

data class EventApiData(
    val id: Int?,
    val title: String,
    val speaker: SpeakerApiData?
)