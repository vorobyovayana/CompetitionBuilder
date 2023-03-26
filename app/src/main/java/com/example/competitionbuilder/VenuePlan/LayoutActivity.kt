package com.example.competitionbuilder.VenuePlan

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.competitionbuilder.CustomTouchListeners.CustomTouchListener
import com.example.competitionbuilder.CustomViews.PisteView
import com.example.competitionbuilder.CustomViews.RectangleView
import com.example.competitionbuilder.MainActivity
import com.example.competitionbuilder.R

class LayoutActivity : AppCompatActivity() {
    var width = 0F
    var height = 0F
    //
    private lateinit var pisteView: PisteView
    private lateinit var btnBack: Button
    var position : Boolean = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)

        var Intent1: Intent
        Intent1 = getIntent()
        width = Intent1.getFloatExtra("width", 0F)
        height = Intent1.getFloatExtra("height", 0F)

        val rectangle = findViewById<RectangleView>(R.id.rectangle_view)
        pisteView = findViewById(R.id.piste_view)
        btnBack = findViewById(R.id.btnBack)
        rectangle.setDimensions(width, height)

        val aspectRatio = rectangle.getAspectRatio()
        pisteView.setAspectRatio(aspectRatio)

        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

//        pisteView.setDimensions(17f*(17f/width), 3f*(17f/width))
        pisteView.setDimensions(17f, 3f)
        // position = true -- means the piste is positioned horizontally,
        // false -- vertically
        position = true

        pisteView.setOnTouchListener(object : CustomTouchListener(this@LayoutActivity) {
            override fun onDoubleClick() {
                super.onDoubleClick()
                try {
                    // Update the dimensions of the PisteView here
                    Log.d("CUSTOMTOUCHLISTENER", "Double click detected")
                    if(position){
                        pisteView.setDimensions(3f, 17f)
                        position = false
                    }else{
                        pisteView.setDimensions(17f, 3f)
                        position = true
                    }
                }
                catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }

        })

    }
}