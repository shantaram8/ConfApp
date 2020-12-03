package kz.kolesateam.confapp.events.presentation

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.presentation.view.BranchAllEventsAdapter
import kz.kolesateam.confapp.events.presentation.view.UpcomingEventsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class BranchAllEventsActivity : AppCompatActivity() {
    private val branchAllEventsViewModel: BranchAllEventsViewModel by viewModel()
    private val branchAllEventsAdapter = BranchAllEventsAdapter()


    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_branch_all_events)
        branchAllEventsViewModel.onStart()
        bindViews()
        branchAllEventsViewModel.getBranchAllEventsLiveData().observe(this, {
            branchAllEventsAdapter.setList(it)
        })
    }
    private fun bindViews() {
        //progressBar = findViewById(R.id.activity_branch_all_events_progress_bar)
        recyclerView = findViewById(R.id.activity_branch_all_events_recycler_view)
        recyclerView.adapter = branchAllEventsAdapter

    }
}