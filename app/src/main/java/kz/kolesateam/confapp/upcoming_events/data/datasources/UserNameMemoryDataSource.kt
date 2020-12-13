package kz.kolesateam.confapp.upcoming_events.data.datasources

class UserNameMemoryDataSource : UserNameDataSource {

    private var userName: String? = null

    override fun getSavedUserName(): String? = userName

    override fun saveUserName(userName: String) {
        this.userName = userName
    }
}