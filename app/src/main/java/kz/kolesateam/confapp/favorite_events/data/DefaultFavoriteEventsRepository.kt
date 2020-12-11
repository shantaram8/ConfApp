package kz.kolesateam.confapp.favorite_events.data

import kz.kolesateam.confapp.favorite_events.domain.FavoriteEventsRepository
import kz.kolesateam.confapp.models.EventApiData
import kz.kolesateam.confapp.models.ResponseData

class DefaultFavoriteEventsRepository: FavoriteEventsRepository {

    private val favoriteEvents: MutableMap<Int, EventApiData> = mutableMapOf()


    override fun saveFavoriteEvents(eventApiData: EventApiData) {
        eventApiData.id ?: return
        favoriteEvents[eventApiData.id] = eventApiData
    }

    override fun removeFavoriteEvent(eventsId: Int?) {
        favoriteEvents.remove(eventsId)
    }

    override fun getAllFavoriteEvents(): ResponseData<List<EventApiData>, Exception> {
        return ResponseData.Success(
                result = favoriteEvents.values.toList()
        )
    }
}