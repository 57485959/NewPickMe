package com.wahyuu.newpickme

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var edtPhone: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var registerNow: TextView
    private lateinit var tvForgotPassword: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        edtPhone = findViewById(R.id.edtPhone)
        edtPassword = findViewById(R.id.edtPassword)
        btnLogin = findViewById(R.id.btnLogin)
        registerNow = findViewById(R.id.registerNow)
        tvForgotPassword = findViewById(R.id.forgotPassword)

//        setupPasswordToggle()

        registerNow.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val phone = edtPhone.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Phone dan password harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE)
            val userJson = sharedPref.getString("user", null)

            if (userJson == null) {
                Toast.makeText(this, "Belum ada akun. Silakan Register.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }

         fun setupPasswordToggle() {
            val ivToggle = findViewById<ImageView>(R.id.passwordToggleIcon)
            var isVisible = false

            // default: password disembunyikan
            edtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            ivToggle.setImageResource(R.drawable.ic_visibility_off)

            ivToggle.setOnClickListener {
                isVisible = !isVisible

                if (isVisible) {
                    // Tampilkan password
                    edtPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    ivToggle.setImageResource(R.drawable.ic_visibility)
                } else {
                    // Sembunyikan password
                    edtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                    ivToggle.setImageResource(R.drawable.ic_visibility_off)
                }

                edtPassword.setSelection(edtPassword.text.length)
            }
        }
    }
}