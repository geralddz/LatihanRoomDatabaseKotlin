package com.app.latihandatabase.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import com.app.latihandatabase.R
import com.app.latihandatabase.database.AppDatabase
import com.app.latihandatabase.database.SharedPref
import com.app.latihandatabase.databinding.ActivityLoginBinding
import com.app.latihandatabase.utils.NotificationChannel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var database: AppDatabase
    private lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPref(this)
        database = AppDatabase.getInstance(applicationContext)

        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmailSignIn.text.toString().trim()
            val pass = binding.etPasswordSignIn.text.toString().trim()
            val user = database.userDao().loginUser(email,pass)
            if(CheckValidation(email,pass)){
                let {
                    user.observe(this, Observer {
                        if (it == null) {
                            Toast.makeText(this, "Masukkan Email Dan Password secara benar", Toast.LENGTH_SHORT).show()
                        } else {
                            sharedPref.setLogIn(true)
                            val username = user.value?.username.toString()
                            val uid = user.value?.uid.toString()
                            sharedPref.setUsername(username)
                            sharedPref.setUid(uid)
                            notification()
                            startActivity(Intent(this, HomeActivity::class.java))
                        }
                    })
                }
            }
        }
    }
    private fun notification(){
        val builder = NotificationCompat.Builder(this, NotificationChannel.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.baseline_library_books_24)
                .setContentTitle("Succeed")
                .setContentText("Selamat Anda Berhasil LOGIN")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
        val notification = builder.build()
        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        notificationManager.notify(1, notification)
    }
    private fun CheckValidation(email: String, pass: String): Boolean {
        if (email.isEmpty()) {
            binding.etEmailSignIn.error = "Masukkan Email Anda"
            binding.etEmailSignIn.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmailSignIn.error = "Masukkan Email dengan Benar"
            binding.etEmailSignIn.requestFocus()
        } else if (pass.isEmpty()) {
            binding.etPasswordSignIn.error = "Masukkan Password Ada"
            binding.etPasswordSignIn.requestFocus()
        } else {
            return true
        }
        Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show()
        return false
    }
}