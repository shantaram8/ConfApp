package kz.kolesateam.confapp.events.presentation

import android.os.Bundle
import android.widget.Button
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
    private lateinit var jsonResponse: TextView
    private lateinit var asyncButton: Button
    private lateinit var syncButton: Button

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
        jsonResponse = findViewById(R.id.activity_upcoming_events_text_view)
        asyncButton = findViewById(R.id.activity_upcoming_events_async_button)
        syncButton = findViewById(R.id.activity_upcoming_events_sync_button)
    }

    private fun loadApiDataAsync() {
        jsonResponse.setTextColor(resources.getColor(R.color.upcoming_events_activity_text_view_color_async))

        apiClient.getUpcomingEvents().enqueue(object : Callback<JsonNode> {
            override fun onResponse(call: Call<JsonNode>, response: Response<JsonNode>) {
                if (response.isSuccessful) {
                    val body: JsonNode = response.body()!!
                    jsonResponse.text = body.toString()
                }
            }

            override fun onFailure(call: Call<JsonNode>, t: Throwable) {
                jsonResponse.setTextColor(resources.getColor(R.color.upcoming_events_activity_text_view_error_color))
                jsonResponse.text = t.localizedMessage
            }
        })
    }

    private fun loadApiDataSync() {
        Thread {
            try {
                val response: Response<JsonNode> = apiClient.getUpcomingEvents().execute()
                val body: JsonNode = response.body()!!
                runOnUiThread {
                    jsonResponse.text = body.toString()
                    jsonResponse.setTextColor(resources.getColor(R.color.upcoming_events_activity_text_view_color_sync))
                }
            } catch (ex: Exception) {
                runOnUiThread {
                    jsonResponse.text = ex.message.toString()
                    jsonResponse.setTextColor(resources.getColor(R.color.upcoming_events_activity_text_view_error_color))
                }

            }
        }.start()
    }
}