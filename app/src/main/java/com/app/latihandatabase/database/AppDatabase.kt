package com.app.latihandatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.latihandatabase.dao.BookDao
import com.app.latihandatabase.dao.UserDao
import com.app.latihandatabase.entity.Book
import com.app.latihandatabase.entity.User

@Database(
    entities = [User::class, Book::class],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun bookDao(): BookDao

    companion object{
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            if(instance==null){
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "DatabaseUser")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }

}