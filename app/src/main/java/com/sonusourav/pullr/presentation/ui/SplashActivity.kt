package com.sonusourav.pullr.presentation.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sonusourav.pullr.R
import kotlinx.android.synthetic.main.activity_splash.splash_view

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initSplash()
        goToMainActivity()
    }

    private fun initSplash() {
        Glide.with(this)
            .load(R.raw.gif_splash)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
            .into(splash_view)
    }

    private fun goToMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                HomeActivity.start(this)
                overridePendingTransition(R.anim.fade_in, 0);
                finish()
            },
            SPLASH_WAITING_TIME
        )
    }

    companion object {
        private const val SPLASH_WAITING_TIME = 4600L
    }
}