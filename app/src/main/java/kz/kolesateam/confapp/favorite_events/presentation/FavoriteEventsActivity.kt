package kz.kolesateam.confapp.favorite_events.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events_details.presentation.EventsDetailsActivity
import kz.kolesateam.confapp.favorite_events.domain.FavoriteEventsRepository
import kz.kolesateam.confapp.favorite_events.presentation.view.FavoriteEventsAdapter
import kz.kolesateam.confapp.models.BranchListItem
import kz.kolesateam.confapp.models.EventApiData
import kz.kolesateam.confapp.upcoming_events.presentation.EVENT_ID
import kz.kolesateam.confapp.upcoming_events.presentation.UpcomingEventsActivity
import kz.kolesateam.confapp.upcoming_events.presentation.UpcomingEventsViewModel
import kz.kolesateam.confapp.upcoming_events.presentation.view.UpcomingEventsClickListeners
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteEventsActivity : AppCompatActivity(), UpcomingEventsClickListeners {

    private val favoriteEventsViewModel: FavoriteEventsViewModel by viewModel()
    private val upcomingEventsViewModel: UpcomingEventsViewModel by viewModel()
    private val favoriteEventsRepository: FavoriteEventsRepository by inject()
    private val favoriteEventsAdapter = FavoriteEventsAdapter(this)

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var toUpcomingEventsButton: Button
    private lateinit var toBackArrowImageView: ImageView

    private fun bindViews() {
        toUpcomingEventsButton = findViewById(R.id.activity_favorite_events_to_upcoming_events_button)
        toBackArrowImageView = findViewById(R.id.activity_favorite_events_back_arrow_image_view)
        progressBar = findViewById(R.id.activity_favorite_events_events_progress_bar)
        recyclerView = findViewById(R.id.activity_favorite_events_recycler_view)
        recyclerView.adapter = favoriteEventsAdapter
    }
    private fun observeFavoriteEventsViewModel() {
        favoriteEventsViewModel.getProgressBarLiveData().observe(this, {
        })
        favoriteEventsViewModel.getBranchAllEventsLiveData().observe(this, {
            favoriteEventsAdapter.setList(it.reversed())
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_events)
        bindViews()
        setOnClickListeners()
        favoriteEventsViewModel.onStart()
        observeFavoriteEventsViewModel()

    }

    private fun setOnClickListeners() {
        toUpcomingEventsButton.setOnClickListener {
            onFavoritesButtonClick()
        }
        toBackArrowImageView.setOnClickListener {
            onBackArrowClick()
        }
    }

    override fun onBranchClick(branchData: BranchListItem) = Unit

    override fun onEventClick(eventData: EventApiData) {
        val intent: Intent = Intent(this, EventsDetailsActivity::class.java)
        intent.putExtra(EVENT_ID, eventData.id)
        startActivity(intent)
    }

    override fun onAddToFavoritesClick(eventData: EventApiData) {
        upcomingEventsViewModel.onAddToFavoriteClick(eventData)
    }

    override fun onFavoritesButtonClick() {
        startActivity(Intent(this, UpcomingEventsActivity::class.java))
    }

    override fun onBackArrowClick() {
        onBackPressed()
    }

}

