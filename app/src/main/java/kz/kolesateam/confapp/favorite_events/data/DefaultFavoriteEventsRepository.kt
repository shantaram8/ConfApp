package kz.kolesateam.confapp.favorite_events.data

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.MapType
import kz.kolesateam.confapp.favorite_events.domain.FavoriteEventsRepository
import kz.kolesateam.confapp.models.EventApiData
import kz.kolesateam.confapp.models.ResponseData
import java.io.FileInputStream
import java.io.FileOutputStream

private const val FAVORITES_EVENTS_FILE_NAME = "favorites_events.json"

class DefaultFavoriteEventsRepository(
        private val context: Context,
        private val objectMapper: ObjectMapper
) : FavoriteEventsRepository {

    private var favoriteEvents: MutableMap<Int, EventApiData> = mutableMapOf()

    init {
        val favoriteEventsFromFile = getFavoriteEventsFromFile()
        favoriteEvents = mutableMapOf()
        favoriteEvents.putAll(favoriteEventsFromFile)

    }

    override fun saveFavoriteEvents(eventApiData: EventApiData) {
        eventApiData.id ?: return
        favoriteEvents[eventApiData.id] = eventApiData
        saveFavoriteEventsToFile()
    }

    override fun removeFavoriteEvent(eventsId: Int?) {
        favoriteEvents.remove(eventsId)
        saveFavoriteEventsToFile()
    }

    override fun getAllFavoriteEvents(): ResponseData<List<EventApiData>, Exception> {

        return ResponseData.Success(
                result = favoriteEvents.values.toList()
        )
    }

    override fun isFavorite(id: Int?): Boolean = favoriteEvents.containsKey(id)

    private fun saveFavoriteEventsToFile() {

        val favoriteEventsJsonString: String = objectMapper.writeValueAsString(favoriteEvents)
        val fileOutputStream: FileOutputStream = context.openFileOutput(
                FAVORITES_EVENTS_FILE_NAME,
                Context.MODE_PRIVATE
        )
        fileOutputStream.write(favoriteEventsJsonString.toByteArray())
        fileOutputStream.close()
    }

    private fun getFavoriteEventsFromFile(): Map<Int, EventApiData> {
        var fileInputStream: FileInputStream? = null

        try {
            fileInputStream = context.openFileInput(FAVORITES_EVENTS_FILE_NAME)
        } catch (exception: Exception) {
            fileInputStream?.close()

            return emptyMap()
        }

        val favoriteEventsJsonString: String = fileInputStream?.bufferedReader()?.readLines()?.joinToString().orEmpty()
        val mapType: MapType = objectMapper.typeFactory.constructMapType(Map::class.java, Int::class.java, EventApiData::class.java)

        return objectMapper.readValue(favoriteEventsJsonString, mapType)
    }
}