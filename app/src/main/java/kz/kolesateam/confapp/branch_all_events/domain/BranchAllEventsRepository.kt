package kz.kolesateam.confapp.branch_all_events.domain

import kz.kolesateam.confapp.models.BranchAllEventsListItem
import kz.kolesateam.confapp.models.EventApiData
import kz.kolesateam.confapp.models.ResponseData

interface BranchAllEventsRepository {

    fun getBranchAllEvents(branchId: Int, branchTitle: String): ResponseData<List<BranchAllEventsListItem>, Exception>
}