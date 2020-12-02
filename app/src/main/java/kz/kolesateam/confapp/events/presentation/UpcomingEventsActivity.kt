package kz.kolesateam.confapp.events.presentation

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.presentation.view.UpcomingEventsAdapter
import kz.kolesateam.confapp.models.ProgressState
import org.koin.androidx.viewmodel.ext.android.viewModel


class UpcomingEventsActivity : AppCompatActivity() {

    private val upcomingEventsViewModel: UpcomingEventsViewModel by viewModel()


    private val adapter = UpcomingEventsAdapter()
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)
        bindViews()

    }

    private fun bindViews() {
        progressBar = findViewById(R.id.activity_upcoming_events_progress_bar)
        recyclerView = findViewById(R.id.activity_upcoming_events_recycler_view)
        recyclerView.adapter = adapter

        observeUpcomingEventsViewModel()
        upcomingEventsViewModel.onStart()
    }

    private fun observeUpcomingEventsViewModel() {
        upcomingEventsViewModel.getProgressLiveData().observe(this, {
            handleProgressBarState(it)
        })
        upcomingEventsViewModel.getUpcomingEventsLiveData().observe(this, {
            showResult(it)
        })
        upcomingEventsViewModel.getErrorLiveData().observe(this, {
            showError(it)
        })
    }

    private fun handleProgressBarState (
        progressState: ProgressState
    ) {
        progressBar.isVisible = progressState is ProgressState.Loading
    }


    private fun showResult(upcomingEventList: List<UpcomingEventListItem>) {
        adapter.setList(upcomingEventList)
    }
    private fun showError(error: Exception) {
        Toast.makeText(this, "Error occurred", Toast.LENGTH_LONG).show()
    }
}





