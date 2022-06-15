package com.example.goodgag.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.goodgag.R
import com.example.goodgag.activity.MainActivity

class SplashActivity : AppCompatActivity() {

    // After 3000 mileSeconds / 3 seconds your next activity will display.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        loadSplashScreen()
    }

    private fun loadSplashScreen() {
        Handler().postDelayed({
            // You can declare your desire activity here to open after finishing splash screen. Like MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }
}
