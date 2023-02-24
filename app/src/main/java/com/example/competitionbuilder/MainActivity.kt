package com.example.competitionbuilder


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.competitionbuilder.AR.ARActivity
import com.example.competitionbuilder.Auth.SignInActivity
import com.example.competitionbuilder.VenuePlan.VenueActivity
import com.example.competitionbuilder.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogOut.setOnClickListener {
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

    }

}