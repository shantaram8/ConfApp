package kz.kolesateam.confapp.events.presentation

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.ApiClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory


val apiRetrofit: Retrofit = Retrofit.Builder()
    .baseUrl("http://37.143.8.68:2020")
    .addConverterFactory(JacksonConverterFactory.create())
    .build()

val apiClient: ApiClient = apiRetrofit.create(ApiClient::class.java)

class UpcomingEventsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)

        val asyncButton: Button = findViewById(R.id.activity_upcoming_events_async_button)
        val syncButton: Button = findViewById(R.id.activity_upcoming_events_sync_button)
        val jsonResult : TextView = findViewById(R.id.activity_upcoming_events_text_view)

        asyncButton.setOnClickListener {

        }
        syncButton.setOnClickListener {

        }

    }
}