package com.example.competitionbuilder.CustomTouchListeners

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.example.competitionbuilder.VenuePlan.LayoutActivity

open class NewCustomTouchListener(layoutActivity: LayoutActivity) : View.OnTouchListener {

    var dX = 0f
    var dY = 0f
    var position = true

    private val gestureDetector = GestureDetector(object : GestureDetector.SimpleOnGestureListener() {
//        override fun onSingleTapUp(event: MotionEvent?): Boolean {
//            // Handle tap gesture
//            return true
//        }

        override fun onLongPress(e: MotionEvent) {
            onLongPress(e)
        }
    })

    open fun onLongPress(e: MotionEvent){
        Log.d("TOUCH_NEW", "LONG press detected")
    }
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        // Let the gesture detector handle the event first
        if (gestureDetector.onTouchEvent(event)) {
            return true
        }

        val x = event.rawX
        val y = event.rawY

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                dX = view.x - x
                dY = view.y - y
                Log.d("StartX", dX.toString())
                Log.d("StartY", dY.toString())
                Log.d("Move", "Move on Piste  is detected")
            }

            MotionEvent.ACTION_MOVE -> {
                view.animate()
                    .x(x + dX)
                    .y(y + dY)
                    .setDuration(0)
                    .start()
                Log.d("EndX", dX.toString())
                Log.d("EndY", dY.toString())
            }

            else -> return false
        }
        return true
    }
}