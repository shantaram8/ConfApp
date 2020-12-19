package kz.kolesateam.confapp.events_details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.events_details.domain.EventDetailsRepository
import kz.kolesateam.confapp.models.EventApiData
import kz.kolesateam.confapp.models.ProgressState
import kz.kolesateam.confapp.models.ResponseData

class EventDetailsViewModel(
    private val eventDetailsRepository: EventDetailsRepository
) : ViewModel() {

    private val eventDetailsLiveData: MutableLiveData<EventApiData> = MutableLiveData()
    private val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()

    fun getEventDetailsLiveData(): LiveData<EventApiData> = eventDetailsLiveData
    fun getProgressBarLiveData(): LiveData<ProgressState> = progressLiveData


    fun getEventDetails(eventId: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            progressLiveData.value = ProgressState.Loading
            val response = withContext(Dispatchers.IO) {
                eventDetailsRepository.getEventsDetails(eventId)
            }
            when (response) {
                is ResponseData.Success -> eventDetailsLiveData.value = response.result
                is ResponseData.Error -> println(response.error)
            }
            progressLiveData.value = ProgressState.Done
        }
    }


}