package com.example.competitionbuilder.VenuePlan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.competitionbuilder.CustomTouchListeners.CustomTouchListener
import com.example.competitionbuilder.CustomViews.PisteView
import com.example.competitionbuilder.CustomViews.RectangleView
import com.example.competitionbuilder.MainActivity
import com.example.competitionbuilder.R

class LayoutActivity : AppCompatActivity() {
    var width = 0F
    var height = 0F
    var rectViewWidth = 0
    var rectViewHeight = 0
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

        val rectangle = findViewById<RectangleView>(R.id.rectangle_view)
        pisteView = findViewById(R.id.piste_view)
        btnBack = findViewById(R.id.btnBack)
        rectangle.setDimensions(width, height)

        val aspectRatio = rectangle.getAspectRatio()
        pisteView.setAspectRatio(aspectRatio)

        pisteView.setRectWidth(rectViewWidth)
        pisteView.setRectHeight(rectViewHeight)
        Log.d("rectViewWidth.layout",rectViewWidth.toString())
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




//        val oneMeter = rectViewWidth/width.toInt()
//        Log.d("rectW.layout", rectViewWidth.toString())
//        Log.d("rectH.layout", rectViewHeight.toString())
        //Log.d("aspectRation",aspectRatio.toString() )



        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

//        pisteView.setDimensions(17f*(17f/width), 3f*(17f/width))



    }

    private inner class MyCustomTouchListener : View.OnTouchListener {

        private var lastX = 0f
        private var lastY = 0f

        override fun onTouch(view: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = event.x
                    lastY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val deltaX = event.x - lastX
                    val deltaY = event.y - lastY
                    view.translationX += deltaX
                    view.translationY += deltaY
                    lastX = event.x
                    lastY = event.y
                }
            }
            return true
        }
    }
}