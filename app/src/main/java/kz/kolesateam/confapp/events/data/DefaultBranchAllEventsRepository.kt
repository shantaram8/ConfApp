package kz.kolesateam.confapp.events.data

import kz.kolesateam.confapp.events.data.datasources.BranchAllEventsDataSource
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.domain.BranchAllEventsRepository
import kz.kolesateam.confapp.models.ResponseData

class DefaultBranchAllEventsRepository(
    private val branchAllEventsDataSource: BranchAllEventsDataSource
) : BranchAllEventsRepository {

    override fun getBranchAllEvents(): ResponseData<List<EventApiData>, Exception> {
        try {
            val branchId = 0
            val responseData = branchAllEventsDataSource.getBranchAllEvents(branchId).execute()
            if (responseData.isSuccessful) {
                val eventApiDataList: List<EventApiData> = responseData.body()!!
                ResponseData.Success(eventApiDataList)
            } else {
                ResponseData.Error(Exception("Response from Branch all events isn't successful"))
            }
        } catch (e: Exception) {
            ResponseData.Error(e)
        }

    }
}