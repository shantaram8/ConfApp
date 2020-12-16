package kz.kolesateam.confapp.favorite_events.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.branch_all_events.domain.BranchAllEventsRepository
import kz.kolesateam.confapp.favorite_events.domain.FavoriteEventsRepository
import kz.kolesateam.confapp.models.*

class FavoriteEventsViewModel(
    private val favoriteEventsRepository: FavoriteEventsRepository
) : ViewModel() {

    private val favoriteEventsLiveData: MutableLiveData<List<EventApiData>> = MutableLiveData()
    private val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()

    fun getBranchAllEventsLiveData(): LiveData<List<EventApiData>> = favoriteEventsLiveData
    fun getProgressBarLiveData(): LiveData<ProgressState> = progressLiveData

    fun onStart() {
        getFavoriteEvents()
    }

    private fun getFavoriteEvents() {
        GlobalScope.launch(Dispatchers.Main) {
            progressLiveData.value = ProgressState.Loading
            val response = withContext(Dispatchers.IO) {
                favoriteEventsRepository.getAllFavoriteEvents()
            }
            when (response) {
                is ResponseData.Success -> favoriteEventsLiveData.value = response.result
                is ResponseData.Error -> println(response.error)
            }
            progressLiveData.value = ProgressState.Done
        }
    }
}