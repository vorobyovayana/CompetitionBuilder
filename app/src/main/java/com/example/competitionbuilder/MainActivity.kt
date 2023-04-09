package com.example.competitionbuilder


import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.competitionbuilder.AR.ARActivity
import com.example.competitionbuilder.Auth.SignInActivity
import com.example.competitionbuilder.CustomTouchListeners.UtilCustomTouchListener
import com.example.competitionbuilder.VenuePlan.VenueActivity
import com.example.competitionbuilder.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

// Activity to show the tips about the app and navigate to features
class MainActivity : AppCompatActivity() {

    var slideIndex: Int = 0
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        var Intent1: Intent
        Intent1 = getIntent()
        val email = Intent1.getStringExtra("email")
        if(!email.equals(null)){
            binding.txtViewHello.setText("Hello, "+email+" !")
        }
        else{
            binding.txtViewHello.isVisible = false
        }

        binding.imgBtnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.btnViewAR.setOnClickListener{
            val intent = Intent(this, ARActivity::class.java)
            startActivity(intent)
        }

        binding.btnPlanVenue.setOnClickListener {
            val intent = Intent(this, VenueActivity::class.java)
            startActivity(intent)
        }

        binding.cardViewInfo.setOnTouchListener(object : UtilCustomTouchListener(this@MainActivity) {
            val txtSlideNum = findViewById<TextView>(R.id.txtViewSlideNum)
            val txtInfo = findViewById<TextView>(R.id.txtViewInfo)

            // Change text when swiping left
            override fun onSwipeLeft() {
                // (override the method from UtilCustomTouchListener)
                super.onSwipeLeft()
                if(slideIndex<3){
                    slideIndex+=1
                    if(slideIndex==0){
                        txtInfo.setText(R.string.txtSlide1)
                        txtSlideNum.setText("1/4")
                    }
                    if(slideIndex==1){
                        txtInfo.setText(R.string.txtSlide2)
                        txtSlideNum.setText("2/4")
                    }
                    if(slideIndex==2){
                        txtInfo.setText(R.string.txtSlide3)
                        txtSlideNum.setText("3/4")
                    }
                    if(slideIndex==3){
                        txtInfo.setText(R.string.txtSlide4)
                        txtSlideNum.setText("4/4")
                    }
                }
                else{
                    txtInfo.setText(R.string.txtSlide4)
                    txtSlideNum.setText("4/4")
                }


            }
            // Change texts when swiping right
            // (override the method from UtilCustomTouchListener)
            override fun onSwipeRight() {
                super.onSwipeRight()

                if(slideIndex>0){
                    slideIndex-=1
                    if(slideIndex==0){
                        txtInfo.setText(R.string.txtSlide1)
                        txtSlideNum.setText("1/4")
                    }
                    if(slideIndex==1){
                        txtInfo.setText(R.string.txtSlide2)
                        txtSlideNum.setText("2/4")
                    }
                    if(slideIndex==2){
                        txtInfo.setText(R.string.txtSlide3)
                        txtSlideNum.setText("3/4")
                    }
                    if(slideIndex==3){
                        txtInfo.setText(R.string.txtSlide4)
                        txtSlideNum.setText("4/4")
                    }
                }
                else{
                    txtInfo.setText(R.string.txtSlide1)
                    txtSlideNum.setText("1/4")
                }

            }

            })


    }

}