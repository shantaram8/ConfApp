package kz.kolesateam.confapp.events.data.datasources

interface UserNameDataSource {

    fun getSavedUserName() : String?

    fun saveUserName(
        userName: String
    )
}