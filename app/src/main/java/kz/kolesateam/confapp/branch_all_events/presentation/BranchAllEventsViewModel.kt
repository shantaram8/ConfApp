package kz.kolesateam.confapp.branch_all_events.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.models.EventApiData
import kz.kolesateam.confapp.branch_all_events.domain.BranchAllEventsRepository
import kz.kolesateam.confapp.models.BranchAllEventsListItem
import kz.kolesateam.confapp.models.ProgressState
import kz.kolesateam.confapp.models.ResponseData

class BranchAllEventsViewModel(
    private val branchAllEventsRepository: BranchAllEventsRepository
) : ViewModel() {

    private val branchAllEventsLiveData: MutableLiveData<List<BranchAllEventsListItem>> = MutableLiveData()
    private val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()
    private val branchTitleLiveData: MutableLiveData<String> = MutableLiveData()

    fun getBranchAllEventsLiveData(): LiveData<List<BranchAllEventsListItem>> = branchAllEventsLiveData
    fun getProgressBarLiveData(): LiveData<ProgressState> = progressLiveData
    fun getBranchTitleLiveData(): LiveData<String> = branchTitleLiveData


    fun onStart(branchId: Int, branchTitle: String) {
        getBranchAllEvents(branchId)
        branchTitleLiveData.value = branchTitle
    }

    private fun getBranchAllEvents(branchId: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            progressLiveData.value = ProgressState.Loading
            val response = withContext(Dispatchers.IO) {
                branchAllEventsRepository.getBranchAllEvents(branchId)
            }
            when (response) {
                is ResponseData.Success -> branchAllEventsLiveData.value = response.result
                is ResponseData.Error -> println(response.error)
            }
            progressLiveData.value = ProgressState.Done
        }
    }
}