package com.app.latihandatabase.ui.fragment

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.latihandatabase.R
import com.app.latihandatabase.database.AppDatabase
import com.app.latihandatabase.databinding.FragmentAddBookBinding
import com.app.latihandatabase.entity.Book
import com.app.latihandatabase.utils.BitmapConverter


class AddBookFragment : Fragment() {

    private lateinit var binding: FragmentAddBookBinding
    private lateinit var database: AppDatabase
    private lateinit var imageUri: Uri
    private lateinit var img:String
    private lateinit var id:String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = AppDatabase.getInstance(requireContext())
        getData()

        binding.tvphotobook.setOnClickListener {
            selectimage()
        }
        binding.btnAddBook.setOnClickListener {
            val judul = binding.etjudul.text.toString()
            val penerbit = binding.etpenerbit.text.toString()
            val tahun = binding.ettahun.text.toString()
            val harga = binding.etharga.text.toString()
            val pesan = arguments?.getInt("bid")
            var photo = ""
            if (pesan!=null){
                database.bookDao().updateBook(
                    Book(
                    id.toInt(),judul,penerbit,tahun,harga.toInt(),img
                    ))
                Toast.makeText(requireContext(), "Data Buku Berhasil DiUpdate", Toast.LENGTH_SHORT).show()
            }else{
                if(img==""){
                    photo = "null"
                    Log.e("photo",photo)
                }else{
                    photo = img
                    Log.e("photo",photo)
                }
                database.bookDao().insertBook(Book(
                    null,judul,penerbit,tahun,harga.toInt(),img
                ))
                Toast.makeText(requireContext(), "Data Buku Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
            }
            fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, BookFragment())
                ?.commit()
        }
    }

    fun getData(){
        val pesan = arguments?.getInt("bid")
        if (pesan!=null){
            val book = database.bookDao().get(pesan)
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
        if (requestCode == 100 && resultCode == AppCompatActivity.RESULT_OK) {
            imageUri = data?.data!!
            val inputStream = requireContext().contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val bitmapstring = BitmapConverter.converterBitmapToString(bitmap)
            binding.ivphotobook.setImageBitmap(bitmap)
            img = bitmapstring
        }
    }
}