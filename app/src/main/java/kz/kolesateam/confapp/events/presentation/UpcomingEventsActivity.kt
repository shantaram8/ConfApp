package kz.kolesateam.confapp.events.presentation

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.presentation.view.UpcomingEventsAdapter
import kz.kolesateam.confapp.network.apiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UpcomingEventsActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private val branchAdapter: UpcomingEventsAdapter = UpcomingEventsAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)
        bindViews()
        loadApiData()

    }

    private fun bindViews() {

        recyclerView = findViewById(R.id.activity_upcoming_events_recycler_view)
        recyclerView.adapter = branchAdapter
    }


    private fun loadApiData() {
        apiClient.getUpcomingEvents().enqueue(object: Callback<List<BranchApiData>>{
            override fun onResponse(
                call: Call<List<BranchApiData>>,
                response: Response<List<BranchApiData>>
            ) {
                if (response.isSuccessful) {
                    branchAdapter.setList(response.body()!!)
                } else {

                }
            }

            override fun onFailure(call: Call<List<BranchApiData>>, t: Throwable) {
            }

        } )
    }

    private fun showError(error: String) {

    }

    private fun showResult(result: List<BranchApiData>) {

    }
}

