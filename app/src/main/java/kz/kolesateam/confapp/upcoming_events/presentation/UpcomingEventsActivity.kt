package kz.kolesateam.confapp.upcoming_events.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.branch_all_events.presentation.BranchAllEventsActivity
import kz.kolesateam.confapp.events_details.presentation.EventsDetailsActivity
import kz.kolesateam.confapp.favorite_events.presentation.FavoriteEventsActivity
import kz.kolesateam.confapp.models.*
import kz.kolesateam.confapp.upcoming_events.presentation.view.UpcomingEventsAdapter
import kz.kolesateam.confapp.upcoming_events.presentation.view.UpcomingEventsClickListeners
import org.koin.androidx.viewmodel.ext.android.viewModel


class UpcomingEventsActivity : AppCompatActivity(), UpcomingEventsClickListeners {

    private val upcomingEventsViewModel: UpcomingEventsViewModel by viewModel()

    private val upcomingEventsAdapter = UpcomingEventsAdapter(this)

    private lateinit var progressBar: ProgressBar
    private lateinit var favoritesButton: Button
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)
        bindViews()

    }

    private fun bindViews() {
        favoritesButton = findViewById(R.id.activity_upcoming_events_favorites_button)
        progressBar = findViewById(R.id.activity_upcoming_events_progress_bar)
        recyclerView = findViewById(R.id.activity_upcoming_events_recycler_view)
        recyclerView.adapter = upcomingEventsAdapter

        favoritesButton.setOnClickListener {
            onFavoritesButtonClick()
        }

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

    private fun handleProgressBarState(
            progressState: ProgressState
    ) {
        progressBar.isVisible = progressState is ProgressState.Loading
    }

    private fun showResult(upcomingEventList: List<UpcomingEventListItem>) {

        upcomingEventsAdapter.setList(upcomingEventList)
    }

    private fun showError(error: Exception) {
        Toast.makeText(this, "Error occurred", Toast.LENGTH_LONG).show()
    }

    override fun onBranchClick(branchData: BranchListItem) {
        val intent: Intent = Intent(this, BranchAllEventsActivity::class.java)
        intent.putExtra("branch_id", branchData.data.id)
        intent.putExtra("branch_title", branchData.data.title)
        startActivity(intent)
    }

    override fun onEventClick(eventData: EventApiData) {
        val intent: Intent = Intent(this, EventsDetailsActivity::class.java)
        intent.putExtra("event_id", eventData.id)
        startActivity(intent)
    }

    override fun onAddToFavoritesClick(eventData: EventApiData) {
        upcomingEventsViewModel.onAddToFavoriteClick(eventData)
    }

    override fun onFavoritesButtonClick() {
        startActivity(Intent(this, FavoriteEventsActivity::class.java))
    }


}





