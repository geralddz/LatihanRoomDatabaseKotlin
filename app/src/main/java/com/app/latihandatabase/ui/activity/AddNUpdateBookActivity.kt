package com.app.latihandatabase.ui.activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.app.latihandatabase.R
import com.app.latihandatabase.database.AppDatabase
import com.app.latihandatabase.database.SharedPref
import com.app.latihandatabase.databinding.ActivityAddNUpdateBookBinding
import com.app.latihandatabase.entity.Book
import com.app.latihandatabase.utils.BitmapConverter

class AddNUpdateBookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNUpdateBookBinding
    private lateinit var sharedPref: SharedPref
    private lateinit var database: AppDatabase
    private lateinit var imageUri: Uri
    private var img:String=""
    private lateinit var id:String
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddNUpdateBookBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = AppDatabase.getInstance(applicationContext)
        sharedPref = SharedPref(this)
        getData()

        binding.tvphotobook.setOnClickListener {
            selectimage()

        }

        binding.btnAddBook.setOnClickListener {
            val judul = binding.etjudul.text.toString()
            val penerbit = binding.etpenerbit.text.toString()
            val tahun = binding.ettahun.text.toString()
            val harga = binding.etharga.text.toString()
            var photo = ""
            val intent = intent.extras
            if (intent!=null){
                database.bookDao().updateBook(Book(
                    id.toInt(),judul,penerbit,tahun,harga.toInt(),img
                ))
                Toast.makeText(this, "Data Buku Berhasil DiUpdate", Toast.LENGTH_SHORT).show()
            }else{
                if(img==""){
                    photo = "null"
                    Log.e("photo",photo)
                }else{
                    photo = img
                    Log.e("photo",photo)
                }
                database.bookDao().insertBook(Book(
                    null,judul,penerbit,tahun,harga.toInt(),photo
                ))
                Toast.makeText(this, "Data Buku Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
            }
            val i = Intent(this, BookActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP ; Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(i)
        }
    }

    fun getData(){
        val intent = intent.extras
        if (intent!=null){
            val book = database.bookDao().get(intent.getInt("id"))
            id = book.bid.toString()
            binding.etjudul.setText(book.judul)
            binding.etpenerbit.setText(book.penerbit)
            binding.ettahun.setText(book.tahun)
            binding.etharga.setText(book.harga.toString())
            img = book.photo.toString()
            if (img=="null"){
                binding.ivphotobook.setImageResource(R.drawable.photo)
            }else{
                val bitmap = BitmapConverter.converterStringToBitmap(img)
                binding.ivphotobook.setImageBitmap(bitmap)
            }
        }
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
            binding.ivphotobook.setImageBitmap(bitmap)
            img = bitmapstring
        }
    }
}