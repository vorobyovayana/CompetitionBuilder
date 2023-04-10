package com.example.competitionbuilder.VenuePlan

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.ColorUtils
import com.example.competitionbuilder.MainActivity
import com.example.competitionbuilder.R
import com.google.firebase.auth.FirebaseAuth

class SaveLayoutPopUp : AppCompatActivity() {
    private var popupTitle = ""
    private var popupText = ""
    private var popupButton = ""
    private var darkStatusBar = false
    private lateinit var popup_window_title: AppCompatTextView
    private lateinit var popup_window_text: AppCompatTextView
    private lateinit var popup_window_button: Button
    private lateinit var popup_window_background: ConstraintLayout
    private lateinit var popup_window_view_with_border: CardView

    var width = 0F
    var height = 0F
    var rectViewWidth = 0
    var rectViewHeight = 0
    var oneMeter = 0F
    var numStrips = 0

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var email: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // disable Activity’s open/close animation
        // by giving the number 0 on the overridePendingTransition
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_pop_up_window)

        // Get the texts for the popup from the previous activity
        val bundle = intent.extras
        popupTitle = bundle?.getString("popuptitle", "Title") ?: ""
        popupText = bundle?.getString("popuptext", "Text") ?: ""
        popupButton = bundle?.getString("popupbtn", "Button") ?: ""
        darkStatusBar = bundle?.getBoolean("darkstatusbar", false) ?: false

        // Get rectangle dimensions, because after the pop up is closed, we need to
        // redraw the rectangle and the piste
        var Intent1: Intent
        Intent1 = getIntent()
        width = Intent1.getFloatExtra("width", 0F)
        height = Intent1.getFloatExtra("height", 0F)
        rectViewWidth = Intent1.getIntExtra("rectViewWidth", 0)
        rectViewHeight = Intent1.getIntExtra("rectViewHeight", 0)
        numStrips = Intent1.getIntExtra("numStrips", 0)

        // Find the views, and set texts
        popup_window_title = findViewById(R.id.popup_window_title)
        popup_window_text = findViewById(R.id.popup_window_text)
        popup_window_button = findViewById(R.id.popup_window_button)
        popup_window_title.text = popupTitle
        popup_window_text.text = popupText
        popup_window_button.text = popupButton

        // Get the background view, so that we can assign onTouch listener to it.
        popup_window_background = findViewById(R.id.popup_window_background)

        // Get current user's email from firebase
        firebaseAuth = FirebaseAuth.getInstance()
        email = firebaseAuth.currentUser!!.email.toString()

        // Setting up colors for  pop up animation
        val alpha = 100 //between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.TRANSPARENT, alphaColor)
        colorAnimation.duration = 500 // milliseconds
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

        popup_window_background.setOnClickListener{
            val intent = Intent(this, LayoutActivity::class.java)
            intent.putExtra("width", width)
            intent.putExtra("height", height)
            intent.putExtra("rectViewWidth", rectViewWidth)
            intent.putExtra("rectViewHeight", rectViewHeight)
            intent.putExtra("numStrips", numStrips)
            startActivity(intent)
        }
    }

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
        intent.putExtra("email", email)
        startActivity(intent)
    }


}