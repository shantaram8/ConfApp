package kz.kolesateam.confapp.upcoming_events.presentation.view

import kz.kolesateam.confapp.models.BranchListItem
import kz.kolesateam.confapp.models.EventApiData

interface UpcomingEventsClickListeners :
    BranchClickListener,
    EventClickListener,
    AddToFavoritesClickListener,
    FavoritesButtonClickListener,
    BackArrowClickListener

interface BranchClickListener {

    fun onBranchClick(
        branchData: BranchListItem
    )
}

interface EventClickListener {

    fun onEventClick(eventData: EventApiData)
}

interface AddToFavoritesClickListener {

    fun onAddToFavoritesClick(
        eventData: EventApiData
    )
}

interface FavoritesButtonClickListener {

    fun onFavoritesButtonClick()
}

interface BackArrowClickListener {

    fun onBackArrowClick()
}
