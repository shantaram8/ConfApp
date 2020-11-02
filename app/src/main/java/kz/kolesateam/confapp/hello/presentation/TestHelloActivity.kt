package kz.kolesateam.confapp.hello.presentation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.APPLICATION_KEY
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.USER_NAME_KEY


class TestHelloActivity : AppCompatActivity() {

    private val closeHelloButton: Button by lazy {
        findViewById(R.id.activity_hello_close_hello_button)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        val nameText: TextView = findViewById(R.id.test_hello_activity_name_text)
        val userName: String = getSavedUserName()
        nameText.text = getString(R.string.hello_text_fmt, userName)

        closeHelloButton.setOnClickListener {
            finish()
        }

    }
    private fun getSavedUserName(): String {
        val sharedPreferences: SharedPreferences = getSharedPreferences(
            APPLICATION_KEY,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(USER_NAME_KEY, "def value") ?: "def value"

    }


}