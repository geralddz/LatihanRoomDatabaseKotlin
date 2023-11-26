package com.app.latihandatabase.ui.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.latihandatabase.utils.BitmapConverter
import com.app.latihandatabase.R
import com.app.latihandatabase.database.AppDatabase
import com.app.latihandatabase.database.SharedPref
import com.app.latihandatabase.databinding.ActivityHomeBinding
import com.app.latihandatabase.ui.fragment.AddBookFragment
import com.app.latihandatabase.ui.fragment.BookFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var database: AppDatabase
    private lateinit var sharedPref: SharedPref
    private lateinit var id:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPref(this)
        getData()

        setSupportActionBar(binding.tbout)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        replacefragment(BookFragment())

        binding.bottomnav.setOnItemSelectedListener {
            val intent = Intent(this, BookActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP ; Intent.FLAG_ACTIVITY_SINGLE_TOP
            when (it.itemId) {
                R.id.editprofile -> startActivity(Intent(this, EditProfileActivity::class.java))
                R.id.listbook -> startActivity(intent)
                R.id.frbook -> replacefragment(BookFragment())
                R.id.fraddbook -> replacefragment(AddBookFragment())
            }
            true
        }

    }

    private fun getData(){
        database = AppDatabase.getInstance(applicationContext)
        id = sharedPref.getUid().toString()
        val user = database.userDao().get(id)
        binding.username.text = user.username
        val img = user.img.toString()
        val bitmap = BitmapConverter.converterStringToBitmap(img)
        binding.ivphoto.setImageBitmap(bitmap)
    }

    private fun replacefragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbarmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btnLogOut -> {
                val builder = android.app.AlertDialog.Builder(this)
                builder.setTitle("Peringatan !!! ")
                    .setMessage("Apakah Anda Ingin Log Out ? ")
                    .setPositiveButton("Yes") { dialog: DialogInterface?, which: Int ->
                        sharedPref.isLogOut()
                        Toast.makeText(this, "Sign Out Berhasil", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }.setNegativeButton("No") { dialog: DialogInterface, which: Int ->
                        dialog.cancel()
                    }.show()
            }
        }
        return true
    }
}