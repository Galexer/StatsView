package ru.netology.statsview

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import ru.netology.statsview.ui.StatsView
import ru.netology.statsview.utils.AndroidUtils

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = findViewById<StatsView>(R.id.stats)

        view.allData = 2000F

        view.data = listOf(
            500F,
            500F,
            500F,
            500F,
        )

//        val rotation = PropertyValuesHolder.ofFloat(View.ROTATION, 0F, 360F)
//        val alpha = ObjectAnimator.ofFloat(view, View.ALPHA, 0.25F, 1F).apply {
//            duration = 300
//            interpolator = LinearInterpolator()
//        }
//        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0F, 1F)
//        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0F, 1F)
//        val scale = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY).apply {
//            duration = 300
//            interpolator = BounceInterpolator()
//        }
//        AnimatorSet().apply {
//            startDelay = 500
//            playSequentially(scale, alpha)
//        }.start()
    }
}