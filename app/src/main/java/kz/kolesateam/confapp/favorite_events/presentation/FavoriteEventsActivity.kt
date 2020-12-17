package kz.kolesateam.confapp.favorite_events.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events_details.presentation.EventsDetailsActivity
import kz.kolesateam.confapp.favorite_events.domain.FavoriteEventsRepository
import kz.kolesateam.confapp.favorite_events.presentation.view.FavoriteEventsAdapter
import kz.kolesateam.confapp.models.BranchListItem
import kz.kolesateam.confapp.models.EventApiData
import kz.kolesateam.confapp.upcoming_events.presentation.view.UpcomingEventsClickListeners
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteEventsActivity : AppCompatActivity(), UpcomingEventsClickListeners {

    private val favoriteEventsViewModel: FavoriteEventsViewModel by viewModel()
    private val favoriteEventsRepository: FavoriteEventsRepository by inject()
    private val favoriteEventsAdapter = FavoriteEventsAdapter(this)


    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var toMainButton: Button

    private fun bindViews() {
        toMainButton = findViewById(R.id.activity_favorite_events_to_main_button)
        progressBar = findViewById(R.id.activity_favorite_events_events_progress_bar)
        recyclerView = findViewById(R.id.activity_favorite_events_recycler_view)
        recyclerView.adapter = favoriteEventsAdapter
    }
    private fun observeFavoriteEventsViewModel() {
        favoriteEventsViewModel.getProgressBarLiveData().observe(this, {
            //handleProgressBarState(it)
        })
        favoriteEventsViewModel.getBranchAllEventsLiveData().observe(this, {
            favoriteEventsAdapter.setList(it.reversed())
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_events)
        bindViews()
        favoriteEventsViewModel.onStart()
        observeFavoriteEventsViewModel()

    }

    override fun onBranchClick(branchData: BranchListItem) {
    }

    override fun onEventClick() {
        startActivity(Intent(this, EventsDetailsActivity::class.java))
    }

    override fun onAddToFavoritesClick(eventData: EventApiData) {
    }

    override fun onFavoritesButtonClick() {
    }

}

