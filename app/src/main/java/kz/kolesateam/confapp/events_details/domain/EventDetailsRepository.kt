package kz.kolesateam.confapp.events_details.domain

import kz.kolesateam.confapp.models.EventApiData
import kz.kolesateam.confapp.models.ResponseData

interface EventDetailsRepository {

    fun getEventsDetails(eventId: Int): ResponseData<EventApiData, Exception>
}