package com.example.competitionbuilder.VenuePlan

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.competitionbuilder.CustomViews.RectangleView
import androidx.core.view.isVisible


import com.example.competitionbuilder.MainActivity
import com.example.competitionbuilder.R
import java.lang.Math.floor
import kotlin.math.floor

class VenueActivity : AppCompatActivity() {
    private lateinit var editTextWidth: EditText
    private lateinit var editTextHeight: EditText
    private lateinit var  txtViewNumStrips: TextView
    private lateinit var btnGoToStrips: Button
    private lateinit var rectangle: RectangleView

    var width = 0F;
    var height = 0F;
    var numStrips = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        editTextWidth = findViewById(R.id.editTextLength)
        editTextHeight = findViewById(R.id.editTextWidth)
        txtViewNumStrips = findViewById(R.id.txtViewNumStrips)
        btnGoToStrips = findViewById(R.id.btnGoToStrips)

        val buttonCreateRectangle = findViewById<Button>(R.id.buttonCreateRectangle)

        buttonCreateRectangle.setOnClickListener {
            try{
            width = editTextWidth.text.toString().toFloat()
            height = editTextHeight.text.toString().toFloat()

            rectangle = findViewById<RectangleView>(R.id.rectangle_view)
                rectangle.isVisible = false
            rectangle.setDimensions(width, height)
                rectangle.isVisible = true
            numStrips = getNumStrips(width, height)
            txtViewNumStrips.setText("You can fit " + numStrips.toString() + " fencing strips in your venue")
            }catch (ex:Exception){
                ex.printStackTrace()
                Toast.makeText(this@VenueActivity, "Please enter both width and length, and make sure that they are numbers", Toast.LENGTH_SHORT ).show()
            }

        }
        btnGoToStrips.setOnClickListener {
            try{
                if(numStrips>0){
                val intent = Intent(this, LayoutActivity::class.java)
                intent.putExtra("width", width)
                intent.putExtra("height", height)
                intent.putExtra("rectViewWidth", rectangle.measuredWidth)
                intent.putExtra("rectViewHeight", rectangle.measuredHeight)
                intent.putExtra("numStrips", numStrips)
                startActivity(intent)
                }
                else{
                    Toast.makeText(this@VenueActivity, "You can't fit any fencing pistes here! You've got to find a bigger venue.", Toast.LENGTH_SHORT ).show()
                }
            }
            catch (ex: Exception){
                ex.printStackTrace()
                Toast.makeText(this@VenueActivity, "Please create the venue model before proceeding to layout creation", Toast.LENGTH_SHORT ).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun getNumStrips(venueLength: Float, venueWidth: Float): Int {
        val pisteWidth = 5
        val pisteLength = 20
        if((venueWidth >=5 && venueLength>=20) || (venueWidth >=20 && venueLength>=5)){
            numStrips= ((venueLength/pisteLength) * (venueWidth/pisteWidth)).toInt()
        }else{
            numStrips =0
        }
        return numStrips

    }




}