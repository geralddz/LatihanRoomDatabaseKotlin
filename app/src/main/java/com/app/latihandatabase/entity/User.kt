package com.app.latihandatabase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    var uid: Int? = null,

    @ColumnInfo("Username")
    var username: String? = null,

    @ColumnInfo("Email")
    var email: String? = null,

    @ColumnInfo("Password")
    var pass: String? = null,

    @ColumnInfo("Address")
    var address: String? = null,

    @ColumnInfo("Phone")
    var phone: String? = null,

    @ColumnInfo("Image")
    var img: String? = null
)