package kz.kolesateam.confapp.events.data

import kz.kolesateam.confapp.events.data.datasources.UpcomingEventsDataSource
import kz.kolesateam.confapp.events.data.datasources.UserNameDataSource
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.BranchListItem
import kz.kolesateam.confapp.events.data.models.HeaderItem
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.utils.model.ResponseData

class DefaultUpcomingEventsRepository(
    private val upcomingEventsDataSource: UpcomingEventsDataSource,
    private val userNameDataSource: UserNameDataSource
) : UpcomingEventsRepository {

    override fun getUpcomingEvents(): ResponseData<List<UpcomingEventListItem>, Exception> {
        return try {
            val response = upcomingEventsDataSource.getUpcomingEvents().execute()

            if (response.isSuccessful) {
                val upcomingEventListItemList: List<UpcomingEventListItem> =
                    listOf(getHeaderItem()) + getBranchItems(response.body()!!)
                ResponseData.Success(upcomingEventListItemList)
            } else {
                ResponseData.Error(Exception("Response not successful"))
            }
        } catch (e: Exception) {
            ResponseData.Error(e)
        }

    }
    private fun getHeaderItem(): UpcomingEventListItem = HeaderItem(
        userName = userNameDataSource.getSavedUserName() ?: ""
    )

    private fun getBranchItems(
        branchList: List<BranchApiData>
    ): List<UpcomingEventListItem> = branchList.map { branchApiData ->
        BranchListItem(
            data = branchApiData
        )
    }


}