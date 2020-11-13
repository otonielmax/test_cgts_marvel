package com.example.testcgts.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testcgts.R
import com.example.testcgts.databinding.ActivitySplashBinding
import java.util.*

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val SPLASH_SCREEN_DELAY: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initApp()
    }

    private fun initApp() {
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                goMain()
            }
        }

        val timer = Timer()
        timer.schedule(task, SPLASH_SCREEN_DELAY)
    }

    private fun goMain() {
        val i = Intent(applicationContext, MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(i)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }
}