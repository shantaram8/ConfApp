package kz.kolesateam.confapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.di.SHARED_PREFS_DATA_SOURCE
import kz.kolesateam.confapp.upcoming_events.data.datasources.UserNameDataSource
import kz.kolesateam.confapp.upcoming_events.presentation.UpcomingEventsActivity
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named


class MainActivity : AppCompatActivity() {

    private val userNameDataSource: UserNameDataSource by inject(named(SHARED_PREFS_DATA_SOURCE))

    private val continueButton: Button by lazy {
        findViewById(R.id.activity_main_continue_button)
    }
    private val mainActivityNameEditText: EditText by lazy {
        findViewById(R.id.activity_main_edit_text)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!userNameDataSource.getSavedUserName().isNullOrEmpty()) {
            navigateToUpcomingEventsScreen()
        }

        mainActivityNameEditText.addTextChangedListener((object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                val isInputEmpty: Boolean = s.toString().isBlank()
                continueButton.isEnabled = !isInputEmpty
            }

        }))

        continueButton.setOnClickListener {
            userNameDataSource.saveUserName(mainActivityNameEditText.text.toString())
            navigateToUpcomingEventsScreen()
        }

    }

    private fun navigateToUpcomingEventsScreen() {
        val upcomingEventsScreenIntent = Intent(this, UpcomingEventsActivity::class.java)
        startActivity(upcomingEventsScreenIntent)
    }


}