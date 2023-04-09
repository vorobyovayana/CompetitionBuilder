package com.example.competitionbuilder.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.competitionbuilder.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.textView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()

            // Check email and password entered by the user
            //If password matches and email is valid, then let the user in
            // Else show error messages
            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        // it is a variable that holds TaskResult
                        if (it.isSuccessful) {
                            val intent = Intent(this, SignInActivity::class.java)
                            // pass the email to the next activity to greet the user there
                            intent.putExtra("email", email)
                            startActivity(intent)
                        }
                        else {
                            Toast.makeText(this, "Invalid email or passwords", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}