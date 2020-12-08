package kz.kolesateam.confapp.models


sealed class BranchAllEventsListItem(
    val type: Int,
)

data class BranchTitleItem(
    val branchTitle: String
) : BranchAllEventsListItem(HEADER_TYPE)

data class EventsListItem(
    val data: EventApiData
) : BranchAllEventsListItem(EVENT_TYPE)