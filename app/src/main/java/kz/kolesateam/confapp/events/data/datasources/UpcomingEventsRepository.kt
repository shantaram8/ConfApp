package kz.kolesateam.confapp.events.data.datasources

import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.network.UPCOMING_EVENTS_DATA_SOURCE
import kz.kolesateam.confapp.utils.model.ResponseData
import retrofit2.Response

class UpcomingEventsRepository(
    private val upcomingEventsDataSource: UpcomingEventsDataSource
) {

    fun getUpcomingEvents(): ResponseData<List<BranchApiData>, String> {
        return try {
            val response: Response<List<BranchApiData>> = UPCOMING_EVENTS_DATA_SOURCE.getUpcomingEvents().execute()

            if (response.isSuccessful) {
                ResponseData.Success(response.body()!!)
            } else {
                ResponseData.Error("Error occurred")
            }
        } catch (e: Exception) {
            ResponseData.Error(e.localizedMessage ?: "Error occurred")
        }
    }
}