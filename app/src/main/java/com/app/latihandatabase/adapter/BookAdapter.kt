package com.app.latihandatabase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.latihandatabase.R
import com.app.latihandatabase.entity.Book
import com.app.latihandatabase.ui.activity.BookActivity
import com.app.latihandatabase.utils.BitmapConverter

class BookAdapter(private var listbook: List<Book>, val bookItemClickInterface: BookActivity,) : RecyclerView.Adapter<BookAdapter.BookViewHolder>(){

    inner class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val judul = view.findViewById<TextView>(R.id.tvjudul)
        val penerbit = view.findViewById<TextView>(R.id.tvpenerbit)
        val tahun = view.findViewById<TextView>(R.id.tvtahun)
        val harga = view.findViewById<TextView>(R.id.tvharga)
        val photo = view.findViewById<ImageView>(R.id.ivitemphoto)
        val edit = view.findViewById<ImageView>(R.id.ivedit)
        val hapus = view.findViewById<ImageView>(R.id.ivdelete)
    }

    interface BookItemClickInterface {
        fun onDelete(position: Int)
        fun onUpdate(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.judul.text = listbook[position].judul
        holder.penerbit.text = listbook[position].penerbit
        holder.tahun.text = listbook[position].tahun
        holder.harga.text = listbook[position].harga.toString()
        val photo = listbook[position].photo.toString()
        if (photo=="null"){
            holder.photo.setImageResource(R.drawable.photo)
        }else{
            val bitmap = BitmapConverter.converterStringToBitmap(listbook[position].photo.toString())
            holder.photo.setImageBitmap(bitmap)
        }


        holder.hapus.setOnClickListener {
            bookItemClickInterface.onDelete(position)
        }

        holder.edit.setOnClickListener {
            bookItemClickInterface.onUpdate(position)
        }
    }

    override fun getItemCount(): Int {
        return listbook.size
    }
}