package kz.kolesateam.confapp.events.presentation

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.di.SHARED_PREFS_DATA_SOURCE
import kz.kolesateam.confapp.events.data.datasources.UpcomingEventsRepository
import kz.kolesateam.confapp.events.data.datasources.UserNameDataSource
import kz.kolesateam.confapp.events.data.datasources.UserNameSharedPrefsDataSource
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.BranchListItem
import kz.kolesateam.confapp.events.data.models.HeaderItem
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem
import kz.kolesateam.confapp.network.UPCOMING_EVENTS_DATA_SOURCE
import kz.kolesateam.confapp.events.presentation.view.UpcomingEventsAdapter
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UpcomingEventsActivity : AppCompatActivity() {

    private val upcomingEventsRepository: UpcomingEventsRepository by inject()
    private val userNameDataSource: UserNameDataSource by inject(named(SHARED_PREFS_DATA_SOURCE))

    private val branchAdapter = UpcomingEventsAdapter()
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)
        bindViews()
        loadApiData()
    }

    private fun bindViews() {
        progressBar = findViewById(R.id.activity_upcoming_events_progress_bar)
        recyclerView = findViewById(R.id.activity_upcoming_events_recycler_view)
        recyclerView.adapter = branchAdapter
    }


    private fun loadApiData() {
        progressBar.visibility = View.VISIBLE

        UPCOMING_EVENTS_DATA_SOURCE.getUpcomingEvents().enqueue(object : Callback<List<BranchApiData>> {
            override fun onResponse(
                call: Call<List<BranchApiData>>,
                response: Response<List<BranchApiData>>
            ) {

                if (response.isSuccessful) {
                    fillAdapterList(response.body()!!)
                }
                progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<List<BranchApiData>>, t: Throwable) {
            }

        })
    }

    private fun fillAdapterList(branchList: List<BranchApiData>) {
        val upcomingEventListItemList: List<UpcomingEventListItem> =
            listOf(getHeaderItem()) + getBranchItems(branchList)

        branchAdapter.setList(upcomingEventListItemList)
    }

    private fun getHeaderItem(): UpcomingEventListItem = HeaderItem(
        userName = getString(R.string.hello_text_fmt, userNameDataSource.getSavedUserName())
    )

    private fun getBranchItems(
        branchList: List<BranchApiData>
    ): List<UpcomingEventListItem> = branchList.map { branchApiData ->
        BranchListItem(
            data = branchApiData
        )
    }


}


