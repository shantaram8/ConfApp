package kz.kolesateam.confapp.upcoming_events.domain

import kz.kolesateam.confapp.models.UpcomingEventListItem
import kz.kolesateam.confapp.models.ResponseData

interface UpcomingEventsRepository {

    fun getUpcomingEvents(): ResponseData<List<UpcomingEventListItem>, Exception>
}