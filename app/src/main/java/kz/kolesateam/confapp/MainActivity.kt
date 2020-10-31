package kz.kolesateam.confapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.hello.presentation.HelloActivity

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val openHelloButton: Button by lazy {
        findViewById(R.id.activity_main_continue_button)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openHelloButton.setOnClickListener {
            val helloScreenIntent = Intent(this, HelloActivity::class.java)
            startActivity(helloScreenIntent)
        }

    }
}