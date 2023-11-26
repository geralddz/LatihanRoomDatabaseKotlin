package com.app.latihandatabase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book (
    @PrimaryKey(autoGenerate = true)
    var bid: Int? = null,

    @ColumnInfo("JudulBuku")
    var judul: String? = null,

    @ColumnInfo("Penerbit")
    var penerbit: String? = null,

    @ColumnInfo("Tahun")
    var tahun: String? = null,

    @ColumnInfo("Harga")
    var harga: Int? = null,

    @ColumnInfo("PhotoBook")
    var photo: String? = null
)