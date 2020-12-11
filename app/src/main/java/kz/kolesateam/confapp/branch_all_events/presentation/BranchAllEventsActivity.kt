package kz.kolesateam.confapp.branch_all_events.presentation

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.models.BranchListItem
import kz.kolesateam.confapp.models.EventApiData
import kz.kolesateam.confapp.branch_all_events.presentation.view.BranchAllEventsAdapter
import kz.kolesateam.confapp.upcoming_events.presentation.view.UpcomingEventsClickListeners
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
        val branchTitle: String = intent.getStringExtra("branch_title") ?: "default_title"
        observeBranchAllEventsViewModel()
        branchAllEventsViewModel.onStart(branchId, branchTitle)
    }

    private fun bindViews() {
        favoritesButton = findViewById(R.id.activity_all_upcoming_events_favorites_button)
        progressBar = findViewById(R.id.activity_branch_all_events_progress_bar)
        recyclerView = findViewById(R.id.activity_branch_all_events_recycler_view)
        recyclerView.adapter = branchAllEventsAdapter
    }

    private fun observeBranchAllEventsViewModel() {
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