package kz.kolesateam.confapp.events.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchListItem
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.presentation.view.BranchAllEventsAdapter
import kz.kolesateam.confapp.events.presentation.view.UpcomingEventsClickListeners
import kz.kolesateam.confapp.models.ProgressState
import org.koin.androidx.viewmodel.ext.android.viewModel

class BranchAllEventsActivity : AppCompatActivity(), UpcomingEventsClickListeners {
    private val branchAllEventsViewModel: BranchAllEventsViewModel by viewModel()

    private val branchAllEventsAdapter = BranchAllEventsAdapter(this)

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoritesButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_branch_all_events)
        bindViews()

        favoritesButton.setOnClickListener {
            onFavoritesButtonClick()
        }

        val branchId: Int = intent.getIntExtra("branch_id", 0)
        observeUpcomingEventsViewModel()
        branchAllEventsViewModel.onStart(branchId)
    }

    private fun bindViews() {
        progressBar = findViewById(R.id.activity_branch_all_events_progress_bar)
        recyclerView = findViewById(R.id.activity_branch_all_events_recycler_view)
        favoritesButton = findViewById(R.id.favorites_button)
        recyclerView.adapter = branchAllEventsAdapter


    }

    private fun observeUpcomingEventsViewModel() {
        branchAllEventsViewModel.getProgressBarLiveData().observe(this, {
            handleProgressBarState(it)
        })
        branchAllEventsViewModel.getBranchAllEventsLiveData().observe(this, {
            branchAllEventsAdapter.setList(it)
        })
    }

    private fun handleProgressBarState(
        progressState: ProgressState
    ) {
        progressBar.isVisible = progressState is ProgressState.Loading
    }

    override fun onBranchClick(branchData: BranchListItem) {
    }

    override fun onEventClick(eventData: EventApiData) {
        Toast.makeText(
            this,
            "${eventData.title} card has been clicked",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onAddToFavoritesClick(eventData: EventApiData) {
        Toast.makeText(
            this,
            "${eventData.speaker?.fullName}'s heart has been clicked",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onFavoritesButtonClick() {
        Toast.makeText(
            this,
            "Favorites button has been clicked",
            Toast.LENGTH_SHORT
        ).show()
    }
}