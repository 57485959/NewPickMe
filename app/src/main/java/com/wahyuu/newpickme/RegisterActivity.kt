package com.wahyuu.newpickme

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.wahyuu.newpickme.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔥 Init Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Tombol daftar ditekan
        binding.btnNextRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // -------------------------------------
            // 🔍 VALIDASI INPUT
            // -------------------------------------
            if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Email tidak valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Password minimal 6 karakter", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (phone.length < 10) {
                Toast.makeText(this, "Nomor HP tidak valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // -------------------------------------
            // 🔍 CEK APAKAH NOMOR HP SUDAH DIGUNAKAN
            // -------------------------------------
            firestore.collection("users")
                .whereEqualTo("phone", phone)
                .get()
                .addOnSuccessListener { docs ->
                    if (!docs.isEmpty) {
                        Toast.makeText(this, "Nomor HP sudah terdaftar", Toast.LENGTH_SHORT).show()
                        return@addOnSuccessListener
                    } else {
                        // Lanjut daftar firebase
                        registerUser(name, email, phone, password)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Gagal cek nomor HP", Toast.LENGTH_SHORT).show()
                }
        }

        // pindah ke login
        binding.tvLoginNow.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    // -------------------------------------
    // 🔥 FUNGSI DAFTAR USER BARU
    // -------------------------------------
    private fun registerUser(name: String, email: String, phone: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->

                val uid = result.user!!.uid

                val userData = hashMapOf(
                    "uid" to uid,
                    "name" to name,
                    "email" to email,
                    "phone" to phone,
                    "createdAt" to System.currentTimeMillis()
                )

                // simpan data user di Firestore
                firestore.collection("users")
                    .document(uid)
                    .set(userData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()

                        // -------------------------------------
                        // 🔥 LANJUT KE OTP SESUAI ALUR KAMU
                        // -------------------------------------
                        val i = Intent(this, GetOtpCodeActivity::class.java)
                        i.putExtra("user_phone", phone)
                        startActivity(i)
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Gagal menyimpan user: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Gagal daftar: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
