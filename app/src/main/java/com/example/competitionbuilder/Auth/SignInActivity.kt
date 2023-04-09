package com.example.competitionbuilder.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.competitionbuilder.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.example.competitionbuilder.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Use this default email
       // email = "demo@demo.com"
        firebaseAuth = FirebaseAuth.getInstance()
        binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            // Check email and password entered by user
            // If we can find a user like this, then let the user in
            // Else show error message
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Incorrect email or password, please try again", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            // pass the email to the next activity to greet the user there
            email = (firebaseAuth.currentUser!!.email).toString()
            intent.putExtra("email", email)
            startActivity(intent)
        }
    }
}