package kz.kolesateam.confapp.events_details.data

import kz.kolesateam.confapp.events_details.data.datasources.EventDetailsDataSource
import kz.kolesateam.confapp.events_details.domain.EventDetailsRepository
import kz.kolesateam.confapp.models.EventApiData
import kz.kolesateam.confapp.models.ResponseData

class DefaultEventDetailsRepository(
        private val eventDetailsDataSource: EventDetailsDataSource
) : EventDetailsRepository {

    override fun getEventsDetails(eventId: Int): ResponseData<EventApiData, Exception> {
        return try {
            val responseData = eventDetailsDataSource.getEventDetails(eventId).execute()
            if (responseData.isSuccessful) {
                val eventDetails: EventApiData = responseData.body()!!
                ResponseData.Success(eventDetails)
            } else {
                val exception = Exception("Response from details of event isn't successful")
                ResponseData.Error(exception)
            }
        } catch (e: Exception) {
            ResponseData.Error(e)
        }
    }
}