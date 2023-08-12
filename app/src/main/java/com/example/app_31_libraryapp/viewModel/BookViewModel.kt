package com.example.app_31_libraryapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_31_libraryapp.repository.Repository
import com.example.app_31_libraryapp.room.BookEntity
import kotlinx.coroutines.launch

class BookViewModel(private val repository: Repository) : ViewModel() {
    fun addBook(book : BookEntity){
        viewModelScope.launch {
            repository.addBookToRoom(book)
        }
    }

    val books = repository.getAllBooks()

    fun deleteBook(book: BookEntity){
        viewModelScope.launch {
            repository.deleteBook(book)
        }
    }

    fun updateBook(book : BookEntity){
        viewModelScope.launch{
            repository.updateBook(book)
        }
    }
}