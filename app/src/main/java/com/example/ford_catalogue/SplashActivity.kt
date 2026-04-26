package com.example.ford_catalogue

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView

class SplashActivity : AppCompatActivity() {

    private val splashDelay: Long = 2200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val ivLogo = findViewById<ImageView>(R.id.ivSplashLogo)
        val tvTitle = findViewById<TextView>(R.id.tvSplashTitle)
        val tvSubtitle = findViewById<TextView>(R.id.tvSplashSubtitle)

        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        ivLogo.startAnimation(fadeIn)
        tvTitle.startAnimation(fadeIn)
        tvSubtitle.startAnimation(fadeIn)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }, splashDelay)
    }
}