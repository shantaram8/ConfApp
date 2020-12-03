package kz.kolesateam.confapp.events.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.domain.BranchAllEventsRepository
import kz.kolesateam.confapp.events.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.models.ProgressState
import kz.kolesateam.confapp.models.ResponseData

class BranchAllEventsViewModel(
    private val branchAllEventsRepository: BranchAllEventsRepository
) : ViewModel() {

    private val branchAllEventsLiveData: MutableLiveData<List<EventApiData>> = MutableLiveData()

    fun getBranchAllEventsLiveData(): LiveData<List<EventApiData>> = branchAllEventsLiveData


    fun onStart() {
        getBranchAllEvents(0)
    }

    private fun getBranchAllEvents(branchId: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.IO) {
                branchAllEventsRepository.getBranchAllEvents(branchId)
            }
            when (response) {
                is ResponseData.Success -> branchAllEventsLiveData.value = response.result
                is ResponseData.Error -> println(response.error)
            }
        }
    }


}