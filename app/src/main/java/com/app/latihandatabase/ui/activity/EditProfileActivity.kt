package com.app.latihandatabase.ui.activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.latihandatabase.utils.BitmapConverter
import com.app.latihandatabase.database.AppDatabase
import com.app.latihandatabase.database.SharedPref
import com.app.latihandatabase.databinding.ActivityEditProfileBinding
import com.app.latihandatabase.entity.User

class EditProfileActivity : AppCompatActivity() {
    private lateinit var database: AppDatabase
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var sharedPref: SharedPref
    private lateinit var imageUri: Uri
    private lateinit var id:String
    private lateinit var pw:String
    private lateinit var img:String
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = AppDatabase.getInstance(applicationContext)
        sharedPref = SharedPref(this)
        getData()

        binding.tvaddphoto.setOnClickListener {
            selectimage()
        }

        binding.btnsaveprofile.setOnClickListener {
            database.userDao().update(
                User(
                    id.toInt(),
                    binding.eteditnama.text.toString(),
                    binding.eteditemail.text.toString(),
                    pw,
                    binding.eteditaddress.text.toString(),
                    binding.eteditphone.text.toString(),
                    img
                )
            )
            Toast.makeText(this, "Update Profile Berhasil", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, HomeActivity::class.java))
        }

    }

    private fun getData(){
        id = sharedPref.getUid().toString()
        val user = database.userDao().get(id)
        binding.eteditnama.setText(user.username)
        binding.eteditemail.setText(user.email)
        binding.eteditaddress.setText(user.address)
        binding.eteditphone.setText(user.phone)
        pw = user.pass.toString()
        img = user.img.toString()
        val bitmap = BitmapConverter.converterStringToBitmap(img)
        binding.ivphoto.setImageBitmap(bitmap)
    }

    private fun selectimage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 100)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            imageUri = data?.data!!
            val inputStream = this.contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val bitmapstring = BitmapConverter.converterBitmapToString(bitmap)
            binding.ivphoto.setImageBitmap(bitmap)
            img = bitmapstring
        }
    }
}