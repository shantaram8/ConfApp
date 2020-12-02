package kz.kolesateam.confapp.events.presentation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.APPLICATION_KEY
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.SHARED_PREFERENCES_KEY
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.BranchListItem
import kz.kolesateam.confapp.events.data.models.HeaderItem
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem
import kz.kolesateam.confapp.network.apiClient
import kz.kolesateam.confapp.events.presentation.view.UpcomingEventsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UpcomingEventsActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var sharedPreferences: SharedPreferences

    private val branchAdapter: UpcomingEventsAdapter = UpcomingEventsAdapter()

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

        apiClient.getUpcomingEvents().enqueue(object : Callback<List<BranchApiData>> {
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
        userName = getString(R.string.hello_text_fmt, getSavedUserName())
    )

    private fun getBranchItems(
        branchList: List<BranchApiData>
    ): List<UpcomingEventListItem> = branchList.map { branchApiData ->
        BranchListItem(
            data = branchApiData
        )
    }

    private fun getSavedUserName(): String {

        sharedPreferences = getSharedPreferences(APPLICATION_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getString(SHARED_PREFERENCES_KEY, "Nothing") ?: "Nothing"
    }
}

