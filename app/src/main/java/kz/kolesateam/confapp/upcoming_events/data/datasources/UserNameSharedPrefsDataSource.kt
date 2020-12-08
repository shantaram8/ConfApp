package kz.kolesateam.confapp.upcoming_events.data.datasources

import android.content.SharedPreferences

private const val SHARED_PREFERENCES_KEY = "user_name"
private const val DEFAULT_USER_NAME = "guest"
private const val EMPTY_STRING = ""

class UserNameSharedPrefsDataSource(
    private val sharedPreferences: SharedPreferences
) : UserNameDataSource {

    override fun getSavedUserName(): String = sharedPreferences
        .getString(SHARED_PREFERENCES_KEY, EMPTY_STRING) ?: DEFAULT_USER_NAME

    override fun saveUserName(
        userName: String
    ) {
        sharedPreferences.edit().putString(SHARED_PREFERENCES_KEY, userName).apply()
    }
}