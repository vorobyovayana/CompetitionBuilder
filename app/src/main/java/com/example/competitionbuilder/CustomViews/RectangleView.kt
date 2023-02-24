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


    private var width: Float = 0F
    private var height: Float = 0F
    private var rectAspectRatio: Float = 0F


    fun setRectAspectRatio(rectAspectRatio: Float){
        this.rectAspectRatio = rectAspectRatio
    }

    fun getAspectRatio(): Float {
        return width / height
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val viewWidth = measuredWidth
        val viewHeight = measuredHeight

        Log.d("viewWidth", viewWidth.toString());
        //  D/viewWidth: 853
        Log.d("viewHeight", viewHeight.toString());
        //D/viewHeight: 1260
        setMeasuredDimension(viewWidth, viewHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val paint = Paint().apply {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 8f
        }

        // Calculate the aspect ratio of the rectangle
        this.setRectAspectRatio(width.toFloat() / height.toFloat())
        Log.d("setRectAspectRatio", this.rectAspectRatio.toString())

        // Calculate the aspect ratio of the view
        val viewAspectRatio = canvas.width.toFloat() / canvas.height.toFloat()

        // Scale the dimensions of the rectangle to fill the view while preserving its aspect ratio
        val rectWidth: Int
        val rectHeight: Int
        if (viewAspectRatio > this.rectAspectRatio) {
            rectWidth = (canvas.height * this.rectAspectRatio).toInt()
            rectHeight = canvas.height
        } else {
            rectWidth = canvas.width
            rectHeight = (canvas.width / this.rectAspectRatio).toInt()
        }

        // Calculate the position of the rectangle in the view
        val rectLeft = (canvas.width - rectWidth) / 2f
        val rectTop = (canvas.height - rectHeight) / 2f
        val rectRight = rectLeft + rectWidth
        val rectBottom = rectTop + rectHeight

        val rect = RectF(rectLeft, rectTop, rectRight, rectBottom)
        canvas.drawRect(rect, paint)
    }

    fun setDimensions(width: Float, height: Float) {
        this.width = width
        this.height = height
        invalidate()
    }
}