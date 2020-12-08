package kz.kolesateam.confapp.upcoming_events.data.datasources

import kz.kolesateam.confapp.models.BranchApiData
import retrofit2.Call
import retrofit2.http.GET

interface UpcomingEventsDataSource {

    @GET("/upcoming_events")
    fun getUpcomingEvents(): Call<List<BranchApiData>>
}