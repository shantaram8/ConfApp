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
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.presentation.view.UpcomingEventsAdapter
import kz.kolesateam.confapp.network.apiClient
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
                    val upcomingEventListItemList: MutableList<UpcomingEventListItem> =
                        mutableListOf()
                    val headerListItem: UpcomingEventListItem =
                        UpcomingEventListItem(
                            type = 1,
                            data =  "Привет, ${getSavedUserName()}!"
                        )
                    val branchListItemList = response.body()!!.map { branchApiData ->
                        UpcomingEventListItem(
                            type = 2,
                            data = branchApiData
                        )
                    }
                    upcomingEventListItemList.add(headerListItem)
                    upcomingEventListItemList.addAll(branchListItemList)

                    branchAdapter.setList(upcomingEventListItemList)

                }
                progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<List<BranchApiData>>, t: Throwable) {
            }

        })
    }

    private fun getSavedUserName() : String {

        sharedPreferences = getSharedPreferences(APPLICATION_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getString(SHARED_PREFERENCES_KEY, "Nothing")?: "Nothing"
    }
}

