package com.example.competitionbuilder.CustomTouchListeners

import android.content.Context
import android.util.Log
import android.view.DragEvent
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat

open class CustomTouchListener(var context: Context) : View.OnTouchListener {
    var gestureDetectorCompat: GestureDetectorCompat

    var dX = 0f
    var dY = 0f


    inner class CustomGestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            return true
            // return super.onDown(e); //base class onDown returns false, must not be used here
        }
        override fun onDoubleTap(e: MotionEvent): Boolean {
            onDoubleClick()
            return super.onDoubleTap(e)
        }

        override fun onLongPress(e: MotionEvent) {
            onMove(e)
            return super.onLongPress(e)
        }
    }

    open fun onDoubleClick() {
        Log.d(
            "GESTURE_DETECTOR",
            "Detected double click in the custom touch listener"
        )
    }

    open fun onMove(motionEvent: MotionEvent){
        Log.d(
            "GESTURE_DETECTOR",
            "Detected move in the custom touch listener"
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
