package kz.kolesateam.confapp.events_details.data.datasources

import kz.kolesateam.confapp.models.EventApiData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EventDetailsDataSource {

    @GET("/events/{eventId}")
    fun getEventDetails(
        @Path("eventId") eventId: Int = 0
    ): Call<EventApiData>

}
