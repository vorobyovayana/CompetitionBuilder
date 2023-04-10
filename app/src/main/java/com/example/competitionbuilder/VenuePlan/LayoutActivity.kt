package com.example.competitionbuilder.VenuePlan


import PisteCustomTouchListener
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.competitionbuilder.CustomViews.PisteView
import com.example.competitionbuilder.CustomViews.RectangleView
import com.example.competitionbuilder.MainActivity
import com.example.competitionbuilder.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
    private lateinit var btnHome: FloatingActionButton
   //    private lateinit var holder: RelativeLayout
    private lateinit var parentView: RelativeLayout
    var position : Boolean = false

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        var Intent1: Intent
        // Get rectangleView dimensions from Intent to redraw it here
        Intent1 = getIntent()
        width = Intent1.getFloatExtra("width", 0F)
        height = Intent1.getFloatExtra("height", 0F)
        rectViewWidth = Intent1.getIntExtra("rectViewWidth", 0)
        rectViewHeight = Intent1.getIntExtra("rectViewHeight", 0)
        numStrips = Intent1.getIntExtra("numStrips", 0)

        val rectangle = findViewById<RectangleView>(R.id.rectangle_view)
        pisteView = findViewById(R.id.piste_view)

        // Draw the rectangle
        rectangle.setDimensions(width, height)

        // Calculate what is a pixel equivalent of one meter
        if(width>height){
            oneMeter = rectViewWidth/width
        }
        else{
            oneMeter = rectViewHeight/height
        }
        // Draw the piste using the dimensions and one meter
        pisteView.setOneMeter(oneMeter)
        pisteView.setDimensions(17f, 3f)
        // Decrease the num of available pistes since one piste is already drawn
        numStrips -=1

        // position = true -- means the piste is positioned horizontally,
        // false -- vertically
        position = true

        // Set on touch listener (double click = change orientation,
        // move (onTouch()) = change pistes location. onTouch is implemented in the PisteCustomTouchListener)
        pisteView.setOnTouchListener(object: PisteCustomTouchListener(pisteView){
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

//            override fun onLongClick() {
//                super.onLongClick()
//                try {
//                    val intent = Intent(pisteView.context, PopUpWindow::class.java)
//
//                    intent.putExtra("popuptitle", "Delete piste?")
//                    intent.putExtra("popuptext", "Would you like to delete this piste?")
//                    intent.putExtra("popupbtn", "Yes")
//                    intent.putExtra("darkstatusbar", false)
//                    intent.putExtra("width", width)
//                    intent.putExtra("height", height)
//                    intent.putExtra("rectViewWidth", rectViewWidth)
//                    intent.putExtra("rectViewHeight", rectViewHeight)
//                    intent.putExtra("numStrips", numStrips)
//
//                    pisteView.context.startActivity(intent)
//                }
//                catch (ex: Exception){
//                    ex.printStackTrace()
//                }
//            }
        })

        parentView = findViewById(R.id.parentView)
        btnAdd = findViewById(R.id.fabAdd)

        // When add btn is clicked, add a new pisteView to the screen and assign the same
        // onTouch listeners to the new piste.
        btnAdd.setOnClickListener {
            Log.d("AddView", "Attempt to add a view detected")
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val newPisteView: PisteView = inflater.inflate(R.layout.piste, null) as PisteView
            //var layoutParams = RelativeLayout.LayoutParams(307, 313)

            // Setting up the layout parameters of the new pisteView, so that it looks right on the screen
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
//            newPisteView.setLayoutParams(
//                RelativeLayout.LayoutParams(
//                    RelativeLayout.LayoutParams.MATCH_PARENT,
//                    RelativeLayout.LayoutParams.MATCH_PARENT
//                )
//            )
            // Decrease the num of available pistes and show a toast of how many more left
            if(numStrips>0){
                numStrips-=1
                parentView.addView(newPisteView, parentView.childCount - 1)
                newPisteView.setOneMeter(oneMeter)
                newPisteView.setDimensions(17f, 3f)
                newPisteView.layoutParams = layoutParams
                val pistesLeft = numStrips.toString()+ " left"
                Toast.makeText(this, pistesLeft, Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "No more pistes left, use the ones that you already have!", Toast.LENGTH_SHORT).show()
            }

            // Add on Touch Listeners, same as above
            newPisteView.setOnTouchListener(object: PisteCustomTouchListener(newPisteView){
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

//                override fun onLongClick() {
//                    super.onLongClick()
//                    try {
//                        val intent = Intent(pisteView.context, PopUpWindow::class.java)
//                        intent.putExtra("popuptitle", "Delete piste?")
//                        intent.putExtra("popuptext", "Would you like to delete this piste?")
//                        intent.putExtra("popupbtn", "Yes")
//                        intent.putExtra("darkstatusbar", false)
//                        intent.putExtra("width", width)
//                        intent.putExtra("height", height)
//                        intent.putExtra("rectViewWidth", rectangle.measuredWidth)
//                        intent.putExtra("rectViewHeight", rectangle.measuredHeight)
//                        intent.putExtra("numStrips", numStrips)
//
//                        pisteView.context.startActivity(intent)
//                    }
//                    catch (ex: Exception){
//                        ex.printStackTrace()
//                    }
//                }
            })
        }
        btnHome = findViewById<FloatingActionButton>(R.id.fabHome)
        btnHome.isVisible=false
        btnNext = findViewById(R.id.fabNext)
        // Save layout to image when
        btnNext.setOnClickListener{
            try {

                Toast.makeText(this, "Congrats, your layout is ready! Feel free to take a screenshot to save your layout. Click on the home button to return to the main page ", Toast.LENGTH_SHORT).show()
                btnHome.isVisible = true
                btnHome.setOnClickListener {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                }
            }catch (ex: Exception){
                ex.printStackTrace()
            }
            //savedBitmapFromViewToFile()
//            val intent = Intent(this, SaveLayoutPopUp::class.java)
//            intent.putExtra("popuptitle", "All done!")
//            intent.putExtra("popuptext", "Click OK to go to the home page")
//            intent.putExtra("popupbtn", "OK")
//            intent.putExtra("darkstatusbar", false)
//            startActivity(intent)
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

    // Method to save the screen to a jpeg
//    @RequiresApi(Build.VERSION_CODES.O)
//    fun saveLayout(){
//        //  Get the view
//        val rootView = window.decorView.rootView
//        // Enable Drawing Cache
//        rootView.isDrawingCacheEnabled = true
//        // Create the bitmap from the drawing cahce
//        val bitmap = Bitmap.createBitmap(rootView.drawingCache)
//        // Get the directory
//        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//        // Format the date and time
//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
//        val current = LocalDateTime.now().format(formatter)
//        // Disable the drawing cache for rootView
//        rootView.isDrawingCacheEnabled = false
//        // Create the file
//        val file = File(directory, "layout_"+current+".jpg")
//        val outputStream = FileOutputStream(file)
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
//        outputStream.flush()
//        outputStream.close()
//        Toast.makeText(this, "Layout image saved successfully", Toast.LENGTH_SHORT).show()
//        outputStream.close()
//        bitmap.recycle()
//    }
}