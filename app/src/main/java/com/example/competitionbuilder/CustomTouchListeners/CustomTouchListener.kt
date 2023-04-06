package com.example.competitionbuilder.CustomTouchListeners

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat

open class CustomTouchListener(var context: Context) : View.OnTouchListener {
    var gestureDetectorCompat: GestureDetectorCompat

    var dX = 0f
    var dY = 0f


    inner class CustomGestureListener : GestureDetector.SimpleOnGestureListener() {
        private val SWIPE_DIST_THRESHOLD = 10
        private val SWIPE_VEL_THRESHOLD = 50

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            onMove(e)
            return super.onSingleTapUp(e)

        }

        override fun onDown(e: MotionEvent): Boolean {
            return true
            // return super.onDown(e); //base class onDown returns false, must not be used here
        }
        override fun onDoubleTap(e: MotionEvent): Boolean {
            onDoubleClick()
            return super.onDoubleTap(e)
        }

        override fun onLongPress(e: MotionEvent) {
            onLongClick(e)
            return super.onLongPress(e)
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent,velocityX: Float, velocityY: Float): Boolean{
            val distX: Float = e2.getX() - e1.getX()
            val distY: Float = e2.getY() - e1.getY()

            Log.d(
                "GESTUREDEMO", "entered fling..." +
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
            } else if (Math.abs(distY) > Math.abs(distX) && Math.abs(distY) > SWIPE_DIST_THRESHOLD && Math.abs(
                    velocityY
                ) > SWIPE_VEL_THRESHOLD
            ) {
//                //vertical swipe has occured
//                if (distY > 0) {
//                    //down swipe
//                    onSwipeDown()
//                } else {
//                    onSwipeUp()
            }
            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }

    open fun onLongClick(e: MotionEvent) {
        Log.d(
            "GESTURE_DETECTOR",
            "Detected long click in the custom touch listener"
        )

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

    open fun onDoubleClick() {
        Log.d(
            "GESTURE_DETECTOR",
            "Detected double click in the custom touch listener"
        )
    }

    open fun onMove(motionEvent: MotionEvent): Boolean{
        Log.d(
            "GESTURE_DETECTOR",
            "Detected move in the custom touch listener"
        )
        return true
    }

    open fun onSwipe(){
        Log.d(
            "GESTURE_DETECTOR",
            "Detected swipe in the custom touch listener"
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