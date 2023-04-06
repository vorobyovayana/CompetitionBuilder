package com.example.competitionbuilder.VenuePlan

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.ColorUtils
import com.example.competitionbuilder.MainActivity
import com.example.competitionbuilder.R

class PopUpWindow : AppCompatActivity() {
    private var popupTitle = ""
    private var popupText = ""
    private var popupButton = ""
    private var darkStatusBar = false
    private lateinit var popup_window_title: AppCompatTextView
    private lateinit var popup_window_text: AppCompatTextView
    private lateinit var popup_window_button: Button
    private lateinit var popup_window_background: ConstraintLayout
    private lateinit var popup_window_view_with_border: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // disable Activity’s open/close animation
        // by giving the number 0 on the overridePendingTransition
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_pop_up_window)

        val bundle = intent.extras
        popupTitle = bundle?.getString("popuptitle", "Title") ?: ""
        popupText = bundle?.getString("popuptext", "Text") ?: ""
        popupButton = bundle?.getString("popupbtn", "Button") ?: ""
        darkStatusBar = bundle?.getBoolean("darkstatusbar", false) ?: false

        popup_window_title = findViewById(R.id.popup_window_title)
        popup_window_text = findViewById(R.id.popup_window_text)
        popup_window_button = findViewById(R.id.popup_window_button)

        popup_window_title.text = popupTitle
        popup_window_text.text = popupText
        popup_window_button.text = popupButton

        popup_window_background = findViewById(R.id.popup_window_background)

        val alpha = 100 //between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.TRANSPARENT, alphaColor)
        colorAnimation.duration = 500 // milliseconds
//        val popup_window_background = findViewById<ConstraintLayout>(R.id.popup_window_background)
        colorAnimation.addUpdateListener { animator ->
            popup_window_background.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.start()

        popup_window_view_with_border = findViewById(R.id.popup_window_view_with_border)

        // Fade animation for the Popup Window
        popup_window_view_with_border.alpha = 0f
        popup_window_view_with_border.animate().alpha(1f).setDuration(500).setInterpolator(
            DecelerateInterpolator()
        ).start()

        popup_window_button.setOnClickListener {
            onBackPressed()
        }


    }

//    private fun setWindowFlag(activity: Activity, on: Boolean) {
//        val win = activity.window
//        val winParams = win.attributes
//        if (on) {
//            winParams.flags = winParams.flags or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//        } else {
//            winParams.flags = winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
//        }
//        win.attributes = winParams
//    }

    override fun onBackPressed() {
        // Fade animation for the background of Popup Window when you press the back button
        val alpha = 100 // between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), alphaColor, Color.TRANSPARENT)
        colorAnimation.duration = 500 // milliseconds
        colorAnimation.addUpdateListener { animator ->
            popup_window_background.setBackgroundColor(
                animator.animatedValue as Int
            )
        }

        // Fade animation for the Popup Window when you press the back button
        popup_window_view_with_border.animate().alpha(0f).setDuration(500).setInterpolator(
            DecelerateInterpolator()
        ).start()

        // After animation finish, close the Activity
        colorAnimation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                finish()
                overridePendingTransition(0, 0)
            }
        })
        colorAnimation.start()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}