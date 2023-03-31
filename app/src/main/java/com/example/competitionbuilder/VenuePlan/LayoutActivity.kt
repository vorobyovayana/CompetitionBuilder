package com.example.competitionbuilder.VenuePlan

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.competitionbuilder.CustomTouchListeners.CustomTouchListener

import com.example.competitionbuilder.CustomTouchListeners.PisteTouchListener
import com.example.competitionbuilder.CustomViews.PisteView
import com.example.competitionbuilder.CustomViews.RectangleView
import com.example.competitionbuilder.MainActivity
import com.example.competitionbuilder.R

class LayoutActivity : AppCompatActivity() {
    var width = 0F
    var height = 0F
    var rectViewWidth = 0
    var rectViewHeight = 0
    var oneMeter = 0F
    var numStrips = 0

    var dX = 0f
    var dY = 0f

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
        rectViewWidth = Intent1.getIntExtra("rectViewWidth", 0)
        rectViewHeight = Intent1.getIntExtra("rectViewHeight", 0)
        numStrips = Intent1.getIntExtra("numStrips", 0)

        val rectangle = findViewById<RectangleView>(R.id.rectangle_view)
        pisteView = findViewById(R.id.piste_view)
        //btnBack = findViewById(R.id.btnBack)
        rectangle.setDimensions(width, height)

        if(width>height){
            oneMeter = rectViewWidth/width
        }
        else{
            oneMeter = rectViewHeight/height
        }
        pisteView.setOneMeter(oneMeter)
        pisteView.setDimensions(17f, 3f)

        // position = true -- means the piste is positioned horizontally,
        // false -- vertically
        position = true

        val txtViewAvailablePistes = findViewById<TextView>(R.id.txtViewAvailablePistes)
        txtViewAvailablePistes.setText("You have "+ numStrips.toString() + " fencing pistes left to put on your venue")

//         Set onTouchListener to move the view OnTouch()
//        val pisteTouchListener = PisteTouchListener()
//        pisteView.setOnTouchListener(pisteTouchListener)

        // Set onTouchListener to change the position of the view OnDoubleClick
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





//        btnBack.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }


    }

}