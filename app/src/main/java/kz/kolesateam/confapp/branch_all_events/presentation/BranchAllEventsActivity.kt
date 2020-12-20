package kz.kolesateam.confapp.branch_all_events.presentation

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.models.BranchListItem
import kz.kolesateam.confapp.models.EventApiData
import kz.kolesateam.confapp.branch_all_events.presentation.view.BranchAllEventsAdapter
import kz.kolesateam.confapp.events_details.presentation.EventsDetailsActivity
import kz.kolesateam.confapp.favorite_events.presentation.FavoriteEventsActivity
import kz.kolesateam.confapp.upcoming_events.presentation.view.UpcomingEventsClickListeners
import kz.kolesateam.confapp.models.ProgressState
import kz.kolesateam.confapp.upcoming_events.presentation.EVENT_ID
import kz.kolesateam.confapp.upcoming_events.presentation.UpcomingEventsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BranchAllEventsActivity : AppCompatActivity(), UpcomingEventsClickListeners {

    private val branchAllEventsViewModel: BranchAllEventsViewModel by viewModel()
    private val upcomingEventsViewModel: UpcomingEventsViewModel by viewModel()
    private val branchAllEventsAdapter = BranchAllEventsAdapter(this)

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoritesButton: Button
    private lateinit var backArrowButton: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_branch_all_events)
        bindViews()
        setOnClickListeners()

        val branchId: Int = intent.getIntExtra("branch_id", 0)
        val branchTitle: String = intent.getStringExtra("branch_title") ?: "default_title"
        observeBranchAllEventsViewModel()
        branchAllEventsViewModel.onStart(branchId, branchTitle)
    }

    private fun setOnClickListeners() {
        favoritesButton.setOnClickListener {
            onFavoritesButtonClick()
        }
        backArrowButton.setOnClickListener {
            onBackArrowClick()
        }
    }

    private fun bindViews() {
        favoritesButton = findViewById(R.id.activity_all_upcoming_events_favorites_button)
        backArrowButton = findViewById(R.id.activity_all_upcoming_events_back_arrow_image_view)
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
        val intent = Intent(this, EventsDetailsActivity::class.java)
        intent.putExtra(EVENT_ID, eventData.id)
        startActivity(intent)
    }

    override fun onAddToFavoritesClick(eventData: EventApiData) {
        upcomingEventsViewModel.onAddToFavoriteClick(eventData)
    }

    override fun onFavoritesButtonClick() {
        startActivity(Intent(this, FavoriteEventsActivity::class.java))
    }

    override fun onBackArrowClick() {
        onBackPressed()
    }
}