package kz.kolesateam.confapp.upcoming_events.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.favorite_events.domain.FavoriteEventsRepository
import kz.kolesateam.confapp.models.*
import kz.kolesateam.confapp.notifications.NotificationAlarmHelper
import kz.kolesateam.confapp.upcoming_events.domain.UpcomingEventsRepository

class UpcomingEventsViewModel(
    private val upcomingEventsRepository: UpcomingEventsRepository,
    private val favoriteEventsRepository: FavoriteEventsRepository,
    private val notificationAlarmHelper: NotificationAlarmHelper
) : ViewModel() {

    private val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()
    private val upcomingEventsLiveData: MutableLiveData<List<UpcomingEventListItem>> =
        MutableLiveData()
    private val errorLiveData: MutableLiveData<Exception> = MutableLiveData()

    fun getProgressLiveData(): LiveData<ProgressState> = progressLiveData
    fun getUpcomingEventsLiveData(): LiveData<List<UpcomingEventListItem>> = upcomingEventsLiveData
    fun getErrorLiveData(): LiveData<Exception> = errorLiveData


    fun onStart() {
        saveUpcomingEventsToLiveData()
    }

    fun onAddToFavoriteClick(
        eventData: EventApiData
    ) {
        when(eventData.isFavorite) {
            true -> {
                favoriteEventsRepository.saveFavoriteEvents(eventData)
                scheduleEvent(eventData)
            }
            else -> favoriteEventsRepository.removeFavoriteEvent(eventsId = eventData.id)
        }
    }

    private fun scheduleEvent(eventData: EventApiData) {
        notificationAlarmHelper.createNotificationAlarm(
                content = eventData.title,
                eventDate  = eventData.startTime
        )
    }

    private fun saveUpcomingEventsToLiveData() {
        GlobalScope.launch(Dispatchers.Main) {
            progressLiveData.value = ProgressState.Loading
            val response = withContext(Dispatchers.IO) {
                upcomingEventsRepository.getUpcomingEvents()
            }
            when (response) {
                is ResponseData.Success -> upcomingEventsLiveData.value = upcomingEventsListWithFavoriteState(response.result)
                is ResponseData.Error -> errorLiveData.value = response.error
            }
            progressLiveData.value = ProgressState.Done
        }
    }
    private fun upcomingEventsListWithFavoriteState(upcomingEventListItem: List<UpcomingEventListItem>) : List<UpcomingEventListItem> {
        upcomingEventListItem.forEach {
            val upcomingEvent: BranchApiData = (it as? BranchListItem)?.data ?: return@forEach
            upcomingEvent.events.forEach { eventApiData ->
                eventApiData.isFavorite = favoriteEventsRepository.isFavorite(eventApiData.id)
            }
        }
        return upcomingEventListItem
    }


}