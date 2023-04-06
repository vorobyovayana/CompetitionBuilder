package com.example.competitionbuilder.VenuePlan

import MyCustomTouchListener
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.competitionbuilder.CustomTouchListeners.CustomTouchListener
import com.example.competitionbuilder.CustomTouchListeners.NewCustomTouchListener

import com.example.competitionbuilder.CustomViews.PisteView
import com.example.competitionbuilder.CustomViews.RectangleView
import com.example.competitionbuilder.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.FileOutputStream

class LayoutActivity : AppCompatActivity() {
    var width = 0F
    var height = 0F
    var rectViewWidth = 0
    var rectViewHeight = 0
    var oneMeter = 0F
    var numStrips = 0


    private lateinit var pisteView: PisteView
    private lateinit var btnAdd: FloatingActionButton
    private lateinit var btnNext: FloatingActionButton
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

//        val myCustomTouchListener = MyCustomTouchListener(pisteView)
//        pisteView.setOnTouchListener(myCustomTouchListener)
        pisteView.setOnTouchListener(object: MyCustomTouchListener(pisteView){
            override fun onDoubleClick() {
                super.onDoubleClick()
                try {

                    if(position){
                        Log.d("Doubleclicktest", "Piste was horizontal")
                        pisteView.setDimensions(3f, 17f)
                        position = false
                    }else{
                        Log.d("Doubleclicktest", "Piste was vertical")
                        pisteView.setDimensions(17f, 3f)
                        position = true
                    }
                }
                catch (ex: Exception) {
                    ex.printStackTrace()
                }

            }
        })



        val parentView = findViewById<RelativeLayout>(R.id.parentView)
        btnAdd = findViewById(R.id.fabAdd)
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

            if(numStrips>0){
                numStrips-=1
                val pistesLeft = numStrips.toString()+ " left"
                Toast.makeText(this, pistesLeft, Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "No more pistes left, use the ones that you already have!", Toast.LENGTH_SHORT).show()
            }

//            newPisteView.setOnTouchListener(pisteTouchListener)
//            Log.d("pisteWidth", newPisteView.getPisteWidth().toString())
            newPisteView.setOnTouchListener(object: MyCustomTouchListener(newPisteView){
                override fun onDoubleClick() {
                    super.onDoubleClick()
                    try {

                        if(position){
                            Log.d("Doubleclicktest", "Piste was horizontal")
                            newPisteView.setDimensions(3f, 17f)
                            position = false
                        }else{
                            Log.d("Doubleclicktest", "Piste was vertical")
                            newPisteView.setDimensions(17f, 3f)
                            position = true
                        }
                    }
                    catch (ex: Exception) {
                        ex.printStackTrace()
                    }

                }
            })
        }

        btnNext = findViewById(R.id.fabNext)
        btnNext.setOnClickListener{
//            val new_intent = Intent(this, ResultActivity::class.java)
//            startActivity(new_intent)
            saveLayout()
            val intent = Intent(this, PopUpWindow::class.java)
            intent.putExtra("popuptitle", "All done!")
            intent.putExtra("popuptext", "Click OK to go to the home page")
            intent.putExtra("popupbtn", "OK")
            intent.putExtra("darkstatusbar", false)
            startActivity(intent)

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

    fun saveLayout(){
        val rootView = window.decorView.rootView
        rootView.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(rootView.drawingCache)
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

        rootView.isDrawingCacheEnabled = false
        val file = File(directory, "layout_image.jpg")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        Toast.makeText(this, "Layout image saved successfully", Toast.LENGTH_SHORT).show()
        outputStream.close()
        bitmap.recycle()

    }



}

