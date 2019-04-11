package com.example.prete.kotlinp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LogInActivity: AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Login Button Action
        login_button_login.setOnClickListener {
            performLogin()
        }

        //Already have an account text field Action
        goback_textview.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    //Login Function
    private fun performLogin() {
        //Login Valuables
        val email = email_edit_login.text.toString()
        val password = password_edit_login.text.toString()

        //Action when text is empty
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter information!", Toast.LENGTH_SHORT).show()
            return
        }

        //Action of Login
        mAuth.signInWithEmailAndPassword(email, password)
                //Register new user if everything is alright
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener
                    Toast.makeText(this, "Successfully logged in!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                }
                //Shows message is something is wrong
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to log in: ${it.message}", Toast.LENGTH_SHORT).show()
                }
    }
}