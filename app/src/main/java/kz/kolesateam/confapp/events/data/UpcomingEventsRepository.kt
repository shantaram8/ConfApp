package kz.kolesateam.confapp.events.data

import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.network.apiClient
import kz.kolesateam.confapp.utils.model.ResponseData
import retrofit2.Response

class UpcomingEventsRepository {

    fun getUpcomingEvents(): ResponseData<List<BranchApiData>, String> {
        return try {
            val response: Response<List<BranchApiData>> = apiClient.getUpcomingEvents().execute()

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