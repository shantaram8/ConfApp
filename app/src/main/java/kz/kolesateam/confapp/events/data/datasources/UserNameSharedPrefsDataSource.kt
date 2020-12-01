package kz.kolesateam.confapp.events.data.datasources

import android.content.SharedPreferences

private const val SHARED_PREFERENCES_KEY = "user_name"
private const val EMPTY_STRING = ""

class UserNameSharedPrefsDataSource(
    private val sharedPreferences: SharedPreferences
): UserNameDataSource {

    override fun getSavedUserName(): String? = sharedPreferences.getString(SHARED_PREFERENCES_KEY, EMPTY_STRING)

    override fun saveUserName(
        userName: String
    ) {
        sharedPreferences.edit().putString(SHARED_PREFERENCES_KEY, userName).apply()
    }
}