package com.example.expensetracker.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import com.example.expensetracker.MainActivity
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            splashScreen()

            // Navigate to the Main Activity after a delay
            LaunchedEffect(Unit) {
                delay(3000) // 3 seconds delay
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish() // Close the Splash Activity
            }
        }
    }
}
