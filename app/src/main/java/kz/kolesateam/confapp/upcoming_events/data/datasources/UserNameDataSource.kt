package kz.kolesateam.confapp.upcoming_events.data.datasources

interface UserNameDataSource {

    fun getSavedUserName(): String?

    fun saveUserName(
        userName: String
    )
}