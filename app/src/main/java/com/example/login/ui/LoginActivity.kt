package com.example.login.ui

    import android.content.Intent
    import android.os.Build
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.util.Patterns
    import android.view.View
    import androidx.annotation.RequiresApi
    import com.example.login.R
    import com.example.login.utils.login
    import com.example.login.utils.toast
    import com.google.firebase.auth.FirebaseAuth
    import kotlinx.android.synthetic.main.activity_login.*
    import kotlinx.android.synthetic.main.activity_login.edit_text_email
    import kotlinx.android.synthetic.main.activity_login.edit_text_password
    import kotlinx.android.synthetic.main.activity_login.progressbar


class LoginActivity : AppCompatActivity() {

        private lateinit var mAuth :FirebaseAuth

        @RequiresApi(Build.VERSION_CODES.FROYO)
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)

            mAuth = FirebaseAuth.getInstance()

            button_sign_in.setOnClickListener {
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

                loginUser(email,password)
            }
            text_view_register.setOnClickListener{
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }

            text_view_forget_password.setOnClickListener {
                startActivity(Intent(this@LoginActivity, ResetPasswordActivity::class.java))

            }

        }

    private fun loginUser(email: String, password: String) {
        progressbar.visibility= View.VISIBLE
        mAuth.signInWithEmailAndPassword(email,password )
            .addOnCompleteListener(this) { task ->
                progressbar.visibility= View.GONE

                if (task.isSuccessful) {
                    login()
                } else {
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
