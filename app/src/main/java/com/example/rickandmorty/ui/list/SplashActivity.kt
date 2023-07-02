package com.example.rickandmorty.ui.list

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import com.example.rickandmorty.R
import com.example.rickandmorty.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val SPLASH_DELAY: Long = 2000
    private val splashScope = CoroutineScope(Dispatchers.Main)

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val scaleAnim = findViewById<ImageView>(R.id.imageRickyView)

        val scaleAnimation =
            AnimatorInflater.loadAnimator(this, R.anim.scale_animation) as AnimatorSet

        scaleAnimation.setTarget(scaleAnim)
        scaleAnimation.start()

        splashScope.launch {
            delay(SPLASH_DELAY)
            navigateToMainActivity()
        }
    }

    private fun navigateToMainActivity() {
        val mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }
    override fun onDestroy() {
        splashScope.cancel()
        super.onDestroy()
    }
}