package com.example.prete.kotlinp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {


    val mAuth = FirebaseAuth.getInstance()
    lateinit var mDatabase: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Register Button Action
        register_button_reg.setOnClickListener {
            performRegister()
        }

        mDatabase = FirebaseDatabase.getInstance().getReference("Usernames")

        //Already have an account text field Action
        already_textview.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }
    }

    //Register function
    private fun performRegister() {
        //Register Valuables
        var username = username_edit_reg.text.toString()
        var email = email_edit_reg.text.toString()
        var password = password_edit_reg.text.toString()

        //Action when text is empty
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter information!", Toast.LENGTH_SHORT).show()
            return
        }

        //Action of registration
        mAuth.createUserWithEmailAndPassword(email, password)
                //Register new user if everything is alright
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener

                    val user = mAuth.currentUser
                    val uid = user!!.uid
                    mDatabase.child(uid).child("Username").setValue(username)
                    Toast.makeText(this, "User successfully created!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                }
                //Shows message is something is wrong
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()
                }
    }

}
