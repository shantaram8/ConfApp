package kz.kolesateam.confapp.events.presentation.view

import kz.kolesateam.confapp.events.data.models.BranchListItem
import kz.kolesateam.confapp.events.data.models.EventApiData

interface UpcomingEventsClickListeners :
    BranchClickListener,
    EventClickListener,
    AddToFavoritesClickListener,
    FavoritesButtonClickListener

interface BranchClickListener {

    fun onBranchClick(
        branchData: BranchListItem
    )
}

interface EventClickListener {

    fun onEventClick(
        eventData: EventApiData
    )
}

interface AddToFavoritesClickListener {

    fun onAddToFavoritesClick(
        eventData: EventApiData
    )
}
interface FavoritesButtonClickListener {

    fun onFavoritesButtonClick()
}
