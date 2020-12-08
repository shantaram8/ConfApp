package kz.kolesateam.confapp.branch_all_events.data.datasources

import kz.kolesateam.confapp.models.EventApiData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BranchAllEventsDataSource {

    @GET("/branch_events/{branchId}")
    fun getBranchAllEvents(
        @Path("branchId") branchId: Int = 0
    ): Call<List<EventApiData>>

}
