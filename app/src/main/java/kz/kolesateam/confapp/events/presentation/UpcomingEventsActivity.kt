package kz.kolesateam.confapp.events.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.SpeakerApiData
import kz.kolesateam.confapp.network.apiClient
import kz.kolesateam.confapp.network.apiClientManual
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UpcomingEventsActivity : AppCompatActivity() {
    private lateinit var jsonResponse: TextView
    private lateinit var asyncButton: Button
    private lateinit var syncButton: Button
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)

        bindViews()
        progressBar.visibility = View.GONE
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
        progressBar = findViewById(R.id.activity_upcoming_events_progress_bar)
    }

    private fun loadApiDataAsync() {
        progressBar.visibility = View.VISIBLE
        apiClient.getUpcomingEvents().enqueue(object : Callback<List<BranchApiData>> {
            override fun onResponse(
                call: Call<List<BranchApiData>>,
                response: Response<List<BranchApiData>>
            ) {

                val responseBody = response.body()!!
                jsonResponse.text = responseBody.toString()
                jsonResponse.setTextColor(resources.getColor(R.color.upcoming_events_activity_text_view_color_async))

            }

            override fun onFailure(call: Call<List<BranchApiData>>, t: Throwable) {
                jsonResponse.text = t.localizedMessage
                jsonResponse.setTextColor(resources.getColor(R.color.upcoming_events_activity_text_view_error_color))
            }
        })
    }

    private fun loadApiDataSync() {
        Thread {
            try {
                runOnUiThread {
                    progressBar.visibility = View.VISIBLE
                }
                val response: Response<ResponseBody> = apiClientManual.getUpcomingEvents().execute()
                val responseBody: ResponseBody = response.body()!!
                val responseJsonString = responseBody.string()
                val responseJsonArray = JSONArray(responseJsonString)
                val apiBranchDataList = parseBranchesJsonArray(responseJsonArray)
                runOnUiThread {
                    progressBar.visibility = View.INVISIBLE
                    jsonResponse.text = apiBranchDataList.toString()
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

    private fun parseBranchesJsonArray(
        responseJsonArray: JSONArray
    ): List<BranchApiData> {
        val branchesList = mutableListOf<BranchApiData>()

        for (ind in 0 until responseJsonArray.length()) {
            val branchJsonObject = (responseJsonArray[ind] as? JSONObject) ?: continue

            val apiBranchData = parseBranchJsonObject(branchJsonObject)

            branchesList.add(apiBranchData)
        }
        return branchesList
    }

    private fun parseBranchJsonObject(
        branchJsonObject: JSONObject
    ): BranchApiData {
        val id = branchJsonObject.getInt("id")
        val title = branchJsonObject.getString("title")

        val eventJsonArray = branchJsonObject.getJSONArray("events")
        val apiEventsList = mutableListOf<EventApiData>()

        for (ind in 0 until eventJsonArray.length()) {
            val eventsJsonObject = (eventJsonArray[ind] as? JSONObject) ?: continue
            val apiEventData = parseEventsJsonObject(eventsJsonObject)
            apiEventsList.add(apiEventData)
        }
        return BranchApiData(
            id = id,
            title = title,
            events = apiEventsList
        )

    }

    private fun parseEventsJsonObject(eventJsonObject: JSONObject): EventApiData {
        val id = eventJsonObject.getInt("id")
        val title = eventJsonObject.getString("title")

        val speakerJsonObject: JSONObject? = (eventJsonObject.get("speaker") as? JSONObject)
        var speakerData: SpeakerApiData? = null

        speakerJsonObject?.let {
            speakerData = parseSpeakerJsonObject(it)
        }

        return EventApiData(
            id = id,
            title = title,
            speaker = speakerData
        )
    }

    private fun parseSpeakerJsonObject(
        speakerJsonObject: JSONObject
    ): SpeakerApiData = SpeakerApiData(
        fullName = speakerJsonObject.getString("fullName")
    )

}