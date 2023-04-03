package com.example.competitionbuilder.VenuePlan

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.competitionbuilder.CustomViews.RectangleView


import com.example.competitionbuilder.MainActivity
import com.example.competitionbuilder.R

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

        editTextWidth = findViewById(R.id.editTextLength)
        editTextHeight = findViewById(R.id.editTextWidth)
        txtViewNumStrips = findViewById(R.id.txtViewNumStrips)
        btnGoToStrips = findViewById(R.id.btnGoToStrips)

        val buttonCreateRectangle = findViewById<Button>(R.id.buttonCreateRectangle)

        buttonCreateRectangle.setOnClickListener {
            width = editTextWidth.text.toString().toFloat()
            height = editTextHeight.text.toString().toFloat()

            rectangle = findViewById<RectangleView>(R.id.rectangle_view)
            rectangle.setDimensions(width, height)

            numStrips = getNumStrips(width, height)
            txtViewNumStrips.setText("You can fit " + numStrips.toString() + " fencing strips in your venue")

        }
        btnGoToStrips.setOnClickListener {
            val intent = Intent(this, LayoutActivity::class.java)

            intent.putExtra("width", width)
            intent.putExtra("height", height)
            intent.putExtra("rectViewWidth", rectangle.measuredWidth)
            intent.putExtra("rectViewHeight", rectangle.measuredHeight)
            intent.putExtra("numStrips", numStrips)
            startActivity(intent)
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

    fun getNumStrips(venueWidth: Float, venueHeight: Float): Int {
        val venueArea = venueHeight * venueWidth;
        // assuming that we need 20 m length and 5 m width
        // to comfortably place the strip and leave spaces in between for walking
        val stripArea = 20 * 5
        return (venueArea / stripArea).toInt();
    }
}