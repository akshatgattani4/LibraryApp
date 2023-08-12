package com.example.app_31_libraryapp.repository

import com.example.app_31_libraryapp.room.BookDB
import com.example.app_31_libraryapp.room.BookEntity

class Repository(val bookDB: BookDB) {
    suspend fun addBookToRoom(bookEntity: BookEntity){
        bookDB.bookDAO().addBook(bookEntity)
    }

    fun getAllBooks() = bookDB.bookDAO().getAllBooks()

    suspend fun deleteBook(bookEntity: BookEntity){
        bookDB.bookDAO().deleteBook(bookEntity)
    }

    suspend fun updateBook(bookEntity: BookEntity){
        bookDB.bookDAO().updateBook(bookEntity)
    }
}