package kz.kolesateam.confapp.events.data.datasources

class UserNameMemoryDataSource: UserNameDataSource {

    private var userName: String? = null

    override fun getSavedUserName(): String? = userName

    override fun saveUserName(userName: String) {
        this.userName = userName
    }
}