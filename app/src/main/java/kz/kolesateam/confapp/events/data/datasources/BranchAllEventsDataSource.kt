package kz.kolesateam.confapp.events.data.datasources

import kz.kolesateam.confapp.events.data.models.EventApiData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BranchAllEventsDataSource {

    @GET("/branch_events/{branchId}")
    fun getBranchAllEvents(
        @Path("branchId") branchId: Int
    ): Call<List<EventApiData>>

}
