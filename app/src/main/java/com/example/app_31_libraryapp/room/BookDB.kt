package com.example.app_31_libraryapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BookEntity::class], version = 1)
abstract class BookDB : RoomDatabase(){
    abstract fun bookDAO() : BookDAO

    companion object{
        @Volatile
        private var instance : BookDB? = null

        fun getInstance(context: Context) : BookDB{
            synchronized(this){
                var ins = instance
                if(ins == null){
                    ins = Room.databaseBuilder(
                        context.applicationContext,
                        BookDB::class.java,
                        "booksDB"
                    ).build()
                    instance = ins
                }
            }
            return instance!!
        }
    }
}