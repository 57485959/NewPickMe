package com.wahyuu.newpickme

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GetOtpCodeActivity : AppCompatActivity() {

    private lateinit var etOtp1: EditText
    private lateinit var etOtp2: EditText
    private lateinit var etOtp3: EditText
    private lateinit var etOtp4: EditText
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_otp)

        etOtp1 = findViewById(R.id.etOtpCode1)
        etOtp2 = findViewById(R.id.etOtpCode2)
        etOtp3 = findViewById(R.id.etOtpCode3)
        etOtp4 = findViewById(R.id.etOtpCode4)
        btnRegister = findViewById(R.id.btnRegister)

        setupOtpEditText(etOtp1, etOtp2)
        setupOtpEditText(etOtp2, etOtp3)
        setupOtpEditText(etOtp3, etOtp4)
        setupOtpEditText(etOtp4, null)

        val txtInfoOtpCode = findViewById<TextView>(R.id.txtInfoOtpCode)

        val phoneNumber = intent.getStringExtra("user_phone") ?: "Nomor tidak ditemukan"

        txtInfoOtpCode.text = "Enter the 4-digit OTP we've just sent to: $phoneNumber"


        btnRegister.setOnClickListener {
            val otp = "${etOtp1.text}${etOtp2.text}${etOtp3.text}${etOtp4.text}"

            if (otp.length < 4) {
                etOtp1.error = "Masukkan kode OTP lengkap"
                return@setOnClickListener
            }

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }
    }

    private fun setupOtpEditText(current: EditText, next: EditText?) {
        current.filters = arrayOf(InputFilter.LengthFilter(1)) // Maksimal 1 digit

        current.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    next?.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    current.setSelection(0)
                }
            }
        })
    }
}
