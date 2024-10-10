package com.jostin.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.jostin.conversorlogin.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Simulate a long loading process
        Handler(Looper.getMainLooper()).postDelayed({
            // Start main activity
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            // Close splash activity
            finish()
        }, 2000) // 2000 ms = 2 seconds
    }
}