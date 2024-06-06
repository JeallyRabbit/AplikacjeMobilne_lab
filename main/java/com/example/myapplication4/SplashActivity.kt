// src/main/java/com/example/yourapp/SplashActivity.kt
package com.example.myapplication4

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 3000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            // Start your app's main activity
            startActivity(Intent(this, MainActivity::class.java))

            // Close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }
}
