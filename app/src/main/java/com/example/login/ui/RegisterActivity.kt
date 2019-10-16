package com.example.login.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import com.example.login.R
import com.example.login.utils.login
import com.example.login.utils.toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.edit_text_email
import kotlinx.android.synthetic.main.activity_register.edit_text_password
import kotlinx.android.synthetic.main.activity_register.progressbar

class RegisterActivity : AppCompatActivity() {

    //stores instance of firebase auth
    private lateinit var  mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth= FirebaseAuth.getInstance()


        button_register.setOnClickListener{
            val email= edit_text_email.text.toString().trim()
            val password = edit_text_password.text.toString().trim()

            if(email.isEmpty()){
                edit_text_email.error="Email Required"
                edit_text_email.requestFocus()
                return@setOnClickListener
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                edit_text_email.error=" Valid Email Required"
                edit_text_email.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty() || password.length < 6){
                edit_text_password.error="6 Character Password Required"
                edit_text_password.requestFocus()
                return@setOnClickListener
            }

            registerUser(email,password)
        }

        text_view_login.setOnClickListener {
             startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
    }

    private fun registerUser(email: String, password: String) {
        progressbar.visibility= View.VISIBLE
        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task->
                progressbar.visibility= View.GONE
                if(task.isSuccessful){
                    //registration success
                    login()
                }else //registration success
                {
                    task.exception?.message?.let{
                        toast(it)
                    }
                }

            }
    }
    override fun onStart() {
        super.onStart()

        mAuth.currentUser?.let{
            login()
        }
    }
}
