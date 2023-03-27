package com.example.competitionbuilder.VenuePlan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.competitionbuilder.CustomViews.RectangleView
import com.example.competitionbuilder.R

class VenueActivity : AppCompatActivity() {
    private lateinit var editTextWidth: EditText
    private lateinit var editTextHeight: EditText
    private lateinit var  txtViewNumStrips: TextView
    private lateinit var btnGoToStrips: Button
    private lateinit var rectangle: RectangleView

    var width = 0F;
    var height = 0F;

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue)

        editTextWidth = findViewById(R.id.editTextWidth)
        editTextHeight = findViewById(R.id.editTextHeight)
        txtViewNumStrips = findViewById(R.id.txtViewNumStrips)
        btnGoToStrips = findViewById(R.id.btnGoToStrips)

        val buttonCreateRectangle = findViewById<Button>(R.id.buttonCreateRectangle)

        buttonCreateRectangle.setOnClickListener {
            width = editTextWidth.text.toString().toFloat()
            height = editTextHeight.text.toString().toFloat()

            rectangle = findViewById<RectangleView>(R.id.rectangle_view)
            rectangle.setDimensions(width, height)

            val numStrips = getNumStrips(width, height)
            txtViewNumStrips.setText(numStrips.toString())

        }
        btnGoToStrips.setOnClickListener {
            val intent = Intent(this, LayoutActivity::class.java)

            intent.putExtra("width", width)
            intent.putExtra("height", height)
            intent.putExtra("rectViewWidth", rectangle.measuredWidth)
            intent.putExtra("rectViewHeight", rectangle.measuredHeight)
            startActivity(intent)
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