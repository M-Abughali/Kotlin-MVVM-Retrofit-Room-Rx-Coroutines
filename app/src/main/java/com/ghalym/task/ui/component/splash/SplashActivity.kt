package com.ghalym.task.ui.component.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.ghalym.task.databinding.ActivitySplashBinding
import com.ghalym.task.ui.component.publicImageList.PublicCatsImagesListActivity
import com.ghalym.task.util.Constants

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigateToMainScreen()
    }


    private fun navigateToMainScreen() {
        Handler().postDelayed({
            val nextScreenIntent = Intent(this, PublicCatsImagesListActivity::class.java)
            startActivity(nextScreenIntent)
            finish()
        }, Constants.SPLASH_DELAY.toLong())
    }
}