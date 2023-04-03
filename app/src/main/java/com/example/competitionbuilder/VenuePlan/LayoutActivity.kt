package com.example.competitionbuilder.VenuePlan

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

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
    private lateinit var btnAdd: Button
    var position : Boolean = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

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
        val pisteTouchListener = PisteTouchListener()
        pisteView.setOnTouchListener(pisteTouchListener)

//        // Set onTouchListener to change the position of the view OnDoubleClick
//        pisteView.setOnTouchListener(object : CustomTouchListener(this@LayoutActivity) {
//
//            override fun onDoubleClick() {
//                super.onDoubleClick()
//                try {
//                    // Update the dimensions of the PisteView here
//                    Log.d("CUSTOMTOUCHLISTENER", "Double click detected")
//                    if(position){
//                        pisteView.setDimensions(3f, 17f)
//                        position = false
//                    }else{
//                        pisteView.setDimensions(17f, 3f)
//                        position = true
//                    }
//                }
//                catch (ex: Exception) {
//                    ex.printStackTrace()
//                }
//            }
//        })

        val parentView = findViewById<RelativeLayout>(R.id.parentView)
        btnAdd = findViewById(R.id.btnAddView)
        btnAdd.setOnClickListener {
            Log.d("AddView", "Attempt to add a view detected")
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val newPisteView: PisteView = inflater.inflate(R.layout.piste, null) as PisteView
            //var layoutParams = RelativeLayout.LayoutParams(307, 313)
            val layoutParams = RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
            Log.d("LAYOUT", (17f*oneMeter).toInt().toString())
            Log.d("LAYOUT", (3f*oneMeter).toInt().toString())
            layoutParams.addRule(RelativeLayout.BELOW, R.id.rectangle_view)
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START)
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END)
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
            layoutParams.marginStart = 61
            layoutParams.topMargin = 33
            layoutParams.marginEnd = 46
            layoutParams.bottomMargin = 47
            newPisteView.layoutParams = layoutParams

            newPisteView.setLayoutParams(
                RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
                )
            )
            parentView.addView(newPisteView, parentView.childCount - 1)
            newPisteView.setOneMeter(oneMeter)
            newPisteView.setDimensions(17f, 3f)
            newPisteView.layoutParams = layoutParams


            newPisteView.setOnTouchListener(pisteTouchListener)
            Log.d("pisteWidth", newPisteView.getPisteWidth().toString())
        }

        }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, VenueActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

//        btnBack.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }


}

