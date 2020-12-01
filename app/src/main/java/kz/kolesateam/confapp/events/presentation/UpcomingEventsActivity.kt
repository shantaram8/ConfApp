package kz.kolesateam.confapp.events.presentation

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.events.presentation.view.UpcomingEventsAdapter
import kz.kolesateam.confapp.utils.model.ResponseData
import org.koin.android.ext.android.inject


class UpcomingEventsActivity : AppCompatActivity() {

    private val upcomingEventsRepository: UpcomingEventsRepository by inject()

    private val adapter = UpcomingEventsAdapter()
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)
        bindViews()
        getUpcomingEvents()
    }

    private fun bindViews() {
        progressBar = findViewById(R.id.activity_upcoming_events_progress_bar)
        recyclerView = findViewById(R.id.activity_upcoming_events_recycler_view)
        recyclerView.adapter = adapter
    }

    private fun getUpcomingEvents() {
        GlobalScope.launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.IO) {
                upcomingEventsRepository.getUpcomingEvents()
            }
            if (response is ResponseData.Success) {
                runOnUiThread {
                    adapter.setList(response.result)
                }
            } else {
                val errorResponse = response as ResponseData.Error
                //progressBar Gone

            }
        }
    }


}





