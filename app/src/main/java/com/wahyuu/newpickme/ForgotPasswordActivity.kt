package com.wahyuu.newpickme

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtNewPassword: EditText
    private lateinit var edtConfirmPassword: EditText
    private lateinit var btnAction: Button
    private lateinit var ivNewPasswordToggle: ImageView
    private lateinit var ivConfirmPasswordToggle: ImageView
    private lateinit var ivBack: ImageView
    private lateinit var newPasswordContainer: RelativeLayout
    private lateinit var confirmPasswordContainer: RelativeLayout

    private var savedUserEmail: String? = null
    private var isEmailVerified = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        initViews()
        setupPasswordToggle(edtNewPassword, ivNewPasswordToggle)
        setupPasswordToggle(edtConfirmPassword, ivConfirmPasswordToggle)

        btnAction.setOnClickListener {
            if (!isEmailVerified) verifyEmail()
            else resetPassword()
        }

        ivBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun initViews() {
        edtEmail = findViewById(R.id.edtEmail)
        edtNewPassword = findViewById(R.id.edtNewPassword)
        edtConfirmPassword = findViewById(R.id.confirmNewPassword)
        btnAction = findViewById(R.id.btnResetPassword)
        ivNewPasswordToggle = findViewById(R.id.newPasswordToggleIcon)
        ivConfirmPasswordToggle = findViewById(R.id.confirmNewPasswordToggleIcon)
        ivBack = findViewById(R.id.ivBack)
        newPasswordContainer = findViewById(R.id.newPasswordContainer)
        confirmPasswordContainer = findViewById(R.id.confirmPasswordContainer)

        newPasswordContainer.visibility = View.GONE
        confirmPasswordContainer.visibility = View.GONE
        btnAction.text = "VERIFIKASI EMAIL"
    }

    private fun verifyEmail() {
        val email = edtEmail.text.toString().trim()
        if (email.isEmpty()) {
            Toast.makeText(this, "Email harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        val sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val userJson = sharedPref.getString("user", null)

        if (userJson == null) {
            Toast.makeText(this, "Belum ada akun terdaftar", Toast.LENGTH_SHORT).show()
            return
        }


        savedUserEmail = email
        isEmailVerified = true
        edtEmail.isEnabled = false
        newPasswordContainer.visibility = View.VISIBLE
        confirmPasswordContainer.visibility = View.VISIBLE
        btnAction.text = "RESET PASSWORD"
        Toast.makeText(this, "Email terverifikasi!", Toast.LENGTH_SHORT).show()
    }

    private fun resetPassword() {
        val newPassword = edtNewPassword.text.toString().trim()
        val confirmPassword = edtConfirmPassword.text.toString().trim()

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }

        if (newPassword != confirmPassword) {
            Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show()
            return
        }

        val sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE)

        Toast.makeText(this, "Password berhasil diubah!", Toast.LENGTH_SHORT).show()

        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun setupPasswordToggle(editText: EditText, toggleIcon: ImageView) {
        var visible = false
        editText.transformationMethod = PasswordTransformationMethod.getInstance()

        toggleIcon.setOnClickListener {
            visible = !visible
            editText.transformationMethod =
                if (visible) HideReturnsTransformationMethod.getInstance()
                else PasswordTransformationMethod.getInstance()

            toggleIcon.setImageResource(
                if (visible) R.drawable.ic_visibility
                else R.drawable.ic_visibility_off
            )
            editText.setSelection(editText.text.length)
        }
    }
}
