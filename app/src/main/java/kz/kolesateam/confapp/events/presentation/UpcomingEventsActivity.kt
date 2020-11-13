package kz.kolesateam.confapp.events.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.databind.JsonNode
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory


val apiRetrofit: Retrofit = Retrofit.Builder()
    .baseUrl("http://37.143.8.68:2020")
    .addConverterFactory(JacksonConverterFactory.create())
    .build()
val apiClient: ApiClient = apiRetrofit.create(ApiClient::class.java)

class UpcomingEventsActivity : AppCompatActivity() {
    private lateinit var jsonResponseTextView: TextView
    private lateinit var asyncButton: Button
    private lateinit var syncButton: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)

        bindViews()
        asyncButton.setOnClickListener {
            loadApiDataAsync()
        }
        syncButton.setOnClickListener {
            loadApiDataSync()
        }

    }

    private fun bindViews() {
        jsonResponseTextView = findViewById(R.id.activity_upcoming_events_response_text_view)
        asyncButton = findViewById(R.id.activity_upcoming_events_async_button)
        syncButton = findViewById(R.id.activity_upcoming_events_sync_button)
        progressBar = findViewById(R.id.activity_upcoming_events_progress_bar)
    }

    private fun loadApiDataAsync() {
        progressBar.visibility = View.VISIBLE
        apiClient.getUpcomingEvents().enqueue(object : Callback<JsonNode> {
            override fun onResponse(call: Call<JsonNode>, response: Response<JsonNode>) {
                if (response.isSuccessful) {
                    val body: JsonNode = response.body()!!
                    jsonResponseTextView.text = body.toString()
                    jsonResponseTextView.setTextColor(resources.getColor(R.color.upcoming_events_activity_text_view_color_async))
                } else {
                    jsonResponseTextView.text = "Server response wasn't successful "
                    jsonResponseTextView.setTextColor(resources.getColor(R.color.upcoming_events_activity_text_view_error_color))
                }
                progressBar.visibility = View.INVISIBLE
            }

            override fun onFailure(call: Call<JsonNode>, t: Throwable) {
                jsonResponseTextView.text = t.localizedMessage
                jsonResponseTextView.setTextColor(resources.getColor(R.color.upcoming_events_activity_text_view_error_color))
                progressBar.visibility = View.INVISIBLE

            }

        })
    }

    private fun loadApiDataSync() {
        progressBar.visibility = View.VISIBLE
        Thread {
            try {
                runOnUiThread{
                }
                val response: Response<JsonNode> = apiClient.getUpcomingEvents().execute()
                val body: JsonNode = response.body()!!
                runOnUiThread {
                    jsonResponseTextView.text = body.toString()
                    jsonResponseTextView.setTextColor(resources.getColor(R.color.upcoming_events_activity_text_view_color_sync))
                    progressBar.visibility = View.INVISIBLE
                }
            } catch (ex: Exception) {
                runOnUiThread {
                    jsonResponseTextView.text = ex.message.toString()
                    jsonResponseTextView.setTextColor(resources.getColor(R.color.upcoming_events_activity_text_view_error_color))
                    progressBar.visibility = View.INVISIBLE
                }

            }
        }.start()
    }
}