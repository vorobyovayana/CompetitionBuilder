package com.example.competitionbuilder.CustomTouchListeners

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat

// CustomTouchListener for the instructions card in the MainActivity
open class UtilCustomTouchListener(var context: Context) : View.OnTouchListener {
    var gestureDetectorCompat: GestureDetectorCompat


    inner class CustomGestureListener : GestureDetector.SimpleOnGestureListener() {
        private val SWIPE_DIST_THRESHOLD = 10
        private val SWIPE_VEL_THRESHOLD = 50


        override fun onDown(e: MotionEvent): Boolean {
            return true
            // return super.onDown(e); //base class onDown returns false, must not be used here
        }

        // Detect swipe
        override fun onFling(e1: MotionEvent, e2: MotionEvent,velocityX: Float, velocityY: Float): Boolean{
            val distX: Float = e2.getX() - e1.getX()
            val distY: Float = e2.getY() - e1.getY()

            Log.d(
                "GESTURE_DETECTOR", "entered fling..." +
                        "distX distY velX velY" + distX + distY
                        + velocityX + velocityY
            )
            if (Math.abs(distX) > Math.abs(distY) && Math.abs(distX) > SWIPE_DIST_THRESHOLD && Math.abs(
                    velocityX
                ) > SWIPE_VEL_THRESHOLD
            ) {
                //horizontal swipe has occured
                if (distX > 0) {
                    //right swipe
                    onSwipeRight()
                } else {
                    onSwipeLeft()
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }


    open fun onSwipeLeft() {
        Log.d(
            "GESTURE_DETECTOR",
            "Detected swipe left in the custom touch listener"
        )
    }

    open fun onSwipeRight() {
        Log.d(
            "GESTURE_DETECTOR",
            "Detected swipe right in the custom touch listener"
        )
    }




    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        //return false; //must return gesture detector's onTouchEvent

        return gestureDetectorCompat.onTouchEvent(motionEvent)
    }

    init {
        gestureDetectorCompat = GestureDetectorCompat(
            context,
            CustomGestureListener()
        )
    }
}