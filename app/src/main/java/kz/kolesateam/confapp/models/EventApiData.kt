package kz.kolesateam.confapp.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class EventApiData(
    @JsonProperty("id")
    val id: Int?,
    @JsonProperty("startTime")
    val startTime: String,
    @JsonProperty("endTime")
    val endTime: String,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("place")
    val place: String,
    @JsonProperty("speaker")
    val speaker: SpeakerApiData?,
    @JsonProperty("isInvited")
    val isInvited: SpeakerApiData?,
    @JsonProperty("photoUrl")
    val photoUrl: SpeakerApiData?,
) {
    var isFavorite: Boolean = false
}