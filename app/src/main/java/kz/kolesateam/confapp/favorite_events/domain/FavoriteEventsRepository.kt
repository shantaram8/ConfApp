package kz.kolesateam.confapp.favorite_events.domain

import kz.kolesateam.confapp.models.BranchAllEventsListItem
import kz.kolesateam.confapp.models.EventApiData
import kz.kolesateam.confapp.models.EventsListItem
import kz.kolesateam.confapp.models.ResponseData

interface FavoriteEventsRepository {

    fun saveFavoriteEvents(
            eventApiData: EventApiData
    )

    fun removeFavoriteEvent(
            eventsId: Int?
    )

    fun getAllFavoriteEvents(): ResponseData<List<EventApiData>, Exception>

    fun isFavorite(id: Int?) : Boolean
}