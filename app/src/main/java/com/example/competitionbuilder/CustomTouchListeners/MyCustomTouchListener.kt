import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import com.example.competitionbuilder.CustomViews.PisteView
import com.example.competitionbuilder.VenuePlan.PopUpWindow

class MyCustomTouchListener(private val pisteView: PisteView): View.OnTouchListener {

    private var initialX: Float = 0.toFloat()
    private var initialY: Float = 0.toFloat()
    private var lastX: Float = 0.toFloat()
    private var lastY: Float = 0.toFloat()
    private val DOUBLE_TAP_TIMEOUT: Long = 1000 // milliseconds
    private var lastTapTime: Long = 0

    private val gestureDetector = GestureDetector(pisteView.context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onLongPress(e: MotionEvent) {
            super.onLongPress(e)
            try {
                val intent = Intent(pisteView.context, PopUpWindow::class.java)
                intent.putExtra("popuptitle", "Delete piste?")
                intent.putExtra("popuptext", "Would you like to delete this piste?")
                intent.putExtra("popupbtn", "Yes")
                intent.putExtra("darkstatusbar", false)
                pisteView.context.startActivity(intent)
            }
            catch (ex: Exception){
                ex.printStackTrace()
            }
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
//            return super.onDoubleTap(e)
            try {
                // Update the dimensions of the PisteView here
                Log.d("CUSTOMTOUCHLISTENER", "Double click detected")
                if(pisteView.getIsVertical()){
                    Log.d("PISTE_POS", pisteView.getIsVertical().toString())
                    pisteView.setDimensions(3f, 17f)
                    pisteView.setIsVertical(true)
                }else{
                    Log.d("PISTE_POS_ELSE", pisteView.getIsVertical().toString())
                    pisteView.setDimensions(17f, 3f)
                    pisteView.setIsVertical(false)
                }
            }
            catch (ex: Exception) {
                ex.printStackTrace()
            }

            return true

        }
    })

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        if (event != null) {
            gestureDetector.onTouchEvent(event)
        }
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                initialX = pisteView.x
                initialY = pisteView.y
                lastX = event.rawX
                lastY = event.rawY
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX: Float = event.rawX - lastX
                val deltaY: Float = event.rawY - lastY
                pisteView.x = initialX + deltaX
                pisteView.y = initialY + deltaY
                return true
            }
            MotionEvent.ACTION_UP -> {
//                val now = System.currentTimeMillis()
//                val doubleTap: Boolean = now - lastTapTime < DOUBLE_TAP_TIMEOUT
//                lastTapTime = now
//                if (doubleTap) {
//                    // TODO: implement double click
//                    Log.d("CUSTOMTOUCHLISTENER", "Double click detected")
//                    try {
//                        // Update the dimensions of the PisteView here
//                        Log.d("CUSTOMTOUCHLISTENER", "Double click detected")
//                        if(pisteView.getIsVertical()){
//                            pisteView.setDimensions(3f, 17f)
//                            pisteView.setIsVertical(true)
//                        }else{
//                            pisteView.setDimensions(17f, 3f)
//                            pisteView.setIsVertical(false)
//                        }
//                    }
//                    catch (ex: Exception) {
//                        ex.printStackTrace()
//                    }
//                }
                return true
            }
            MotionEvent.ACTION_CANCEL -> {
                return true
            }
            MotionEvent.ACTION_OUTSIDE -> {
                return true
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                return true
            }
            MotionEvent.ACTION_POINTER_UP -> {
                return true
            }
            MotionEvent.ACTION_SCROLL -> {
                return true
            }

            else -> return false
        }
    }
}