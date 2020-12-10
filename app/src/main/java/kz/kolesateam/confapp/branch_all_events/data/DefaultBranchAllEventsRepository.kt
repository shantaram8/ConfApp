package kz.kolesateam.confapp.branch_all_events.data

import kz.kolesateam.confapp.branch_all_events.data.datasources.BranchAllEventsDataSource
import kz.kolesateam.confapp.branch_all_events.domain.BranchAllEventsRepository
import kz.kolesateam.confapp.branch_all_events.presentation.view.BranchAllEventsViewHolder
import kz.kolesateam.confapp.models.*
import kz.kolesateam.confapp.upcoming_events.data.datasources.UpcomingEventsDataSource

class DefaultBranchAllEventsRepository(
        private val branchAllEventsDataSource: BranchAllEventsDataSource
) : BranchAllEventsRepository {

    override fun getBranchAllEvents(branchId: Int, branchTitle: String): ResponseData<List<BranchAllEventsListItem>, Exception> {
        return try {
            val responseData = branchAllEventsDataSource.getBranchAllEvents(branchId).execute()
            if (responseData.isSuccessful) {
                val eventApiDataList: List<BranchAllEventsListItem> = listOf(getHeaderItem(branchTitle)) +  getBranchItems(responseData.body()!!)
                ResponseData.Success(eventApiDataList)
            } else {
                val exception = Exception("Response from Branch all events isn't successful")
                ResponseData.Error(exception)
            }
        } catch (e: Exception) {
            ResponseData.Error(e)
        }
    }
    private fun getHeaderItem(branchTitle: String): BranchAllEventsListItem = BranchTitleItem(
        branchTitle = branchTitle
    )

    private fun getBranchItems(
        eventsList: List<EventApiData>
    ): List<BranchAllEventsListItem> = eventsList.map { eventApiData ->
        EventsListItem(
            eventApiData
        )
    }
}