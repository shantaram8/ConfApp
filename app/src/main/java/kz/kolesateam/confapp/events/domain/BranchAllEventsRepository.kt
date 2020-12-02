package kz.kolesateam.confapp.events.domain

import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.models.ResponseData

interface BranchAllEventsRepository {

    fun getBranchAllEvents(): ResponseData<List<EventApiData>, Exception>
}