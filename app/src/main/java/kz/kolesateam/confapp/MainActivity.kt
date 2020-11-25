package kz.kolesateam.confapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity


const val APPLICATION_KEY = "application"
const val SHARED_PREFERENCES_KEY = "application"

class MainActivity : AppCompatActivity() {
    private val continueButton: Button by lazy {
        findViewById(R.id.activity_main_continue_button)
    }
    private val mainActivityNameEditText: EditText by lazy {
        findViewById(R.id.activity_main_edit_text)
    }
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mainActivityNameEditText.addTextChangedListener((object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                val isInputEmpty: Boolean = s.toString().isBlank()
                continueButton.isEnabled = !isInputEmpty
            }

        }))

        continueButton.setOnClickListener {
            saveUserName(mainActivityNameEditText.text.toString())
            navigateToUpcomingEventsScreen()
        }

    }

    private fun saveUserName(userName: String) {
        preferences = getSharedPreferences(APPLICATION_KEY, MODE_PRIVATE)
        preferences.edit().putString(SHARED_PREFERENCES_KEY, userName).apply()
    }

    private fun navigateToUpcomingEventsScreen() {
        val upcomingEventsScreenIntent = Intent(this, UpcomingEventsActivity::class.java)
        startActivity(upcomingEventsScreenIntent)
    }


}