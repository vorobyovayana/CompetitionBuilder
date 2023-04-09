package com.example.competitionbuilder.CustomViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View

class RectangleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Rectangle dimensions from user
    private var width: Float = 0F
    private var height: Float = 0F
    // The dimensions of the view in pixels
    private var viewWidth: Int = 0
    private var viewHeight: Int = 0
    // Width/Height of the rectangle -- calculated to determine how
    // to correctly scale the rectangle view in the screen
    private var rectAspectRatio: Float = 0F

    fun setRectAspectRatio(rectAspectRatio: Float){
        this.rectAspectRatio = rectAspectRatio
    }

//    fun setViewHeight(vHeight: Int){
//        this.viewHeight = vHeight
//    }
//    fun setViewWidth(vWidth: Int){
//        this.viewWidth = vWidth
//    }

    // Get the width and length of the view in pixels
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        viewWidth = measuredWidth
        viewHeight = measuredHeight

        Log.d("viewWidth", viewWidth.toString());
        //  D/viewWidth: 853
        Log.d("viewHeight", viewHeight.toString());
        //D/viewHeight: 1260
        setMeasuredDimension(viewWidth, viewHeight)
    }

    // Method that draws the rectangle
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Configure the color and style of the future rectangle
        val paint = Paint().apply {
            color = Color.parseColor("#7900FF")
            style = Paint.Style.STROKE
            strokeWidth = 8f
        }

        // Calculate the aspect ratio of the rectangle
        this.setRectAspectRatio(width.toFloat() / height.toFloat())
//        this.setViewWidth(viewWidth)
//        this.setViewHeight(viewHeight)

        // Calculate the aspect ratio of the view
        val viewAspectRatio = canvas.width.toFloat() / canvas.height.toFloat()

        // Scale the dimensions of the rectangle to fill the view while preserving its aspect ratio
        val rectWidth: Int
        val rectHeight: Int
        // If the viewAspectRatio is bigger than rectAspectRatio
        // Then make the rectanlge's height the same as the view's height,
        // and then scale the rectangle's width accordingly.
        if (viewAspectRatio > this.rectAspectRatio) {
            rectWidth = (canvas.height * this.rectAspectRatio).toInt()
            rectHeight = canvas.height

        // Else make the rectanlge's width the same as the view's width,
        // and then scale the rectangle's height accordingly
        } else {
            rectWidth = canvas.width
            rectHeight = (canvas.width / this.rectAspectRatio).toInt()
        }

        // Calculate the position of the rectangle in the view
        val rectLeft = (canvas.width - rectWidth) / 2f
        val rectTop = (canvas.height - rectHeight) / 2f
        val rectRight = rectLeft + rectWidth
        val rectBottom = rectTop + rectHeight

        // put float coordinates of the rectangle
        val rect = RectF(rectLeft, rectTop, rectRight, rectBottom)
        // draw the rectangle using the coordinates and color
        canvas.drawRect(rect, paint)
    }

    // Triggers the drawing of a new rectangle
    fun setDimensions(width: Float, height: Float) {
        this.width = width
        this.height = height
        // invalidate() = redraw the view
        invalidate()
    }
}