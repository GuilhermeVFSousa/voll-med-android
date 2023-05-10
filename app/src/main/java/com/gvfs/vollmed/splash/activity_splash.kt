package com.gvfs.vollmed.splash

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.gvfs.vollmed.MainActivity
import com.gvfs.vollmed.R


class activity_splash : AppCompatActivity() {

    private val SPLASH_DISPLAY_LENGTH = 4000L
    private val FADE_DURATION = 3000L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val splashImage = findViewById<ImageView>(R.id.splashImage)

        val animator = ObjectAnimator.ofPropertyValuesHolder(
            splashImage,
            PropertyValuesHolder.ofFloat("alpha", 0f, 1f)
        )
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = FADE_DURATION
        animator.start()

        Handler().postDelayed(Runnable {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, SPLASH_DISPLAY_LENGTH)

    }
}