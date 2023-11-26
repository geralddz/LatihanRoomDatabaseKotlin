package com.app.latihandatabase.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.app.latihandatabase.database.AppDatabase
import com.app.latihandatabase.databinding.ActivityRegisterBinding
import com.app.latihandatabase.entity.User

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var database: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = AppDatabase.getInstance(applicationContext)

        binding.btnSignUp.setOnClickListener {
            val nama = binding.etNamaSignUp.text.toString().trim()
            val email = binding.etEmailSignUp.text.toString().trim()
            val pass = binding.etPasswordSignUp.text.toString().trim()

            if(CheckValidation(nama, email, pass)){
                database.userDao().registerUser(User(null,nama, email, pass))
                Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

    }

    private fun CheckValidation(nama: String, email: String, pass: String): Boolean {
        if(nama.isEmpty()){
            binding.etNamaSignUp.error = "Masukkan Nama Anda"
            binding.etNamaSignUp.requestFocus()
        } else if (email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmailSignUp.error = "Masukkan Email Anda dengan Benar"
            binding.etEmailSignUp.requestFocus()
        } else if (pass.isEmpty()) {
            binding.etPasswordSignUp.error = "Masukkan Password Anda"
            binding.etPasswordSignUp.requestFocus()
        } else {
            binding.etPasswordSignUp.error = null
            return true
        }
        Toast.makeText(this, "Registrasi Gagal", Toast.LENGTH_SHORT).show()
        return false
    }
}