package com.app.latihandatabase.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.latihandatabase.adapter.BookAdapter
import com.app.latihandatabase.database.AppDatabase
import com.app.latihandatabase.database.SharedPref
import com.app.latihandatabase.databinding.ActivityBookBinding
import com.app.latihandatabase.entity.Book

class BookActivity : AppCompatActivity(), BookAdapter.BookItemClickInterface  {
    private lateinit var binding: ActivityBookBinding
    private lateinit var database: AppDatabase
    private lateinit var sharedPref: SharedPref
    private lateinit var bookAdapter: BookAdapter
    private lateinit var imageUri: Uri
    private var img :String = ""
    private var dataimg :String = ""
    private lateinit var dataBitmap: Bitmap
    private lateinit var id:String
    private var listbook = mutableListOf<Book>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPref(this)
        database = AppDatabase.getInstance(applicationContext)

        bookAdapter = BookAdapter(listbook,this)
        binding.rvBook.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = bookAdapter
            bookAdapter.notifyDataSetChanged()
        }

        binding.btnfloat.setOnClickListener {
            val intent = Intent(this, AddNUpdateBookActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP ; Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    fun getData(){
        listbook.clear()
        listbook.addAll(database.bookDao().getAll())
        bookAdapter.notifyDataSetChanged()
    }

    override fun onDelete(position: Int) {
        database.bookDao().deleteBook(listbook[position])
        getData()
    }

    override fun onUpdate(position: Int) {
        val intent = Intent(this, AddNUpdateBookActivity::class.java)
        intent.putExtra("id", listbook[position].bid)
        startActivity(intent)
    }

}
