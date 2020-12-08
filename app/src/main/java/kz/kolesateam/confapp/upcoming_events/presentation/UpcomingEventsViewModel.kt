package kz.kolesateam.confapp.upcoming_events.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.models.UpcomingEventListItem
import kz.kolesateam.confapp.upcoming_events.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.models.ProgressState
import kz.kolesateam.confapp.models.ResponseData

class UpcomingEventsViewModel(
    private val upcomingEventsRepository: UpcomingEventsRepository
) : ViewModel() {

    private val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()
    private val upcomingEventsLiveData: MutableLiveData<List<UpcomingEventListItem>> =
        MutableLiveData()
    private val errorLiveData: MutableLiveData<Exception> = MutableLiveData()

    fun getProgressLiveData(): LiveData<ProgressState> = progressLiveData
    fun getUpcomingEventsLiveData(): LiveData<List<UpcomingEventListItem>> = upcomingEventsLiveData
    fun getErrorLiveData(): LiveData<Exception> = errorLiveData


    fun onStart() {
        getUpcomingEvents()
    }

    private fun getUpcomingEvents() {
        GlobalScope.launch(Dispatchers.Main) {
            progressLiveData.value = ProgressState.Loading
            val response = withContext(Dispatchers.IO) {
                upcomingEventsRepository.getUpcomingEvents()
            }
            when (response) {
                is ResponseData.Success -> upcomingEventsLiveData.value = response.result
                is ResponseData.Error -> errorLiveData.value = response.error
            }
            progressLiveData.value = ProgressState.Done
        }
    }


}