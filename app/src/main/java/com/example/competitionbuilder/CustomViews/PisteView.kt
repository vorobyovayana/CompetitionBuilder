package com.example.competitionbuilder.CustomViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View


class PisteView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    // Piste dimensions (always 17f, 3f or 17f, 3f)
    // We need to have it as a variable though, because the user
    // can change the orientation of the piste
    private var width: Float = 0F
    private var height: Float = 0F

    // One meter equivalent in pixels
    private var oneMeter: Float = 0F
    // Piste dimensions in pixels
    private var pisteWidth: Int  = 0
    private var pisteHeight: Int  = 0

    fun setOneMeter(oneM: Float){
        this.oneMeter = oneM
        invalidate()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Configure the color and style of the piste
        val paint = Paint().apply {
            color = Color.parseColor("#7900FF")
            style = Paint.Style.STROKE
            strokeWidth = 8f
        }

        // Configure the color and style of the area around the piste
        // (We draw with a dash line area that can't be occupied by anything,
        // to reserve enough space for the piste)
        val dashPaint = Paint().apply {
            color = Color.parseColor("#36454F")
            style = Paint.Style.STROKE
            strokeWidth = 4f
            // Make the dash line
            pathEffect = android.graphics.DashPathEffect(floatArrayOf(20f, 20f), 0f)
        }

        // Get actual rectWidth and rectHeight (in pixels) from rectangle
        // Divide the width in pixels to the width entered by user - to get 1 meter
        // Multiply 17f and number in pixels for one meter to get the actual width in pixels
        // Do the same for height
        pisteWidth = (this.width * this.oneMeter).toInt()
        pisteHeight = (this.height * this.oneMeter).toInt()

        // Calculate the position of the rectangle in the view
        val rectLeft = (canvas.width - pisteWidth) / 2f
        val rectTop = (canvas.height - pisteHeight) / 2f
        val rectRight = rectLeft + pisteWidth
        val rectBottom = rectTop + pisteHeight

        // Draw the piste itself
        val rect = RectF(rectLeft, rectTop, rectRight, rectBottom)
        canvas.drawRect(rect, paint)

        // Draw the dash line around the piste to signify the space that cannot be occupied because of the equipment
        val dashRect = RectF(rectLeft - 20f, rectTop - 20f, rectRight + 20f, rectBottom + 20f)
        canvas.drawRect(dashRect, dashPaint)

        Log.d("Doubleclicktest", pisteHeight.toString())
        Log.d("Doubleclicktest", pisteWidth.toString())
    }

    // Trigger the onDraw() and redraw the piste
    fun setDimensions(width: Float, height: Float) {
        this.width = width
        this.height = height
        Log.d("Doubleclicktest", "trying to redraw")
        Log.d("Doubleclicktest", this.width.toString()+"this is width")
        Log.d("Doubleclicktest", this.height.toString())
        invalidate()
    }

}
