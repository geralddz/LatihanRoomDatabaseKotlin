package com.app.latihandatabase.database

import android.content.Context
import android.content.SharedPreferences


class SharedPref(context: Context) {
    val PRIVATE_MODE = 0
    private val PREF_NAME = "SharedPreferences"
    private val IS_LOGIN = "is_login"

    var pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    var editor: SharedPreferences.Editor? = pref.edit()

    fun setUsername(name: String) {
        editor?.putString("name", name)
        editor?.commit()
    }

    fun getUserame(): String? {
        return pref.getString("name", "")
    }

    fun setUid(uid: String) {
        editor?.putString("uid", uid)
        editor?.commit()
    }

    fun getUid(): String? {
        return pref.getString("uid", "")
    }


    fun setLogIn(isLogin: Boolean) {
        editor?.putBoolean(IS_LOGIN, isLogin)
        editor?.commit()
    }

    fun isLogIn(): Boolean {
        return pref.getBoolean(IS_LOGIN, false)
    }

    fun isLogOut() {
        editor?.clear()
        editor?.commit()
    }
}