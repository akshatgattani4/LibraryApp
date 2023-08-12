package com.example.app_31_libraryapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.app_31_libraryapp.room.BookEntity
import com.example.app_31_libraryapp.viewModel.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(viewModel: BookViewModel, bookId: String?, navController: NavHostController) {

    var input by remember {
        mutableStateOf("")
    }

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(value = input,
            onValueChange = { txt -> input = txt },
            label = { Text(text = "Book Name") },
            placeholder = { Text(text = "Enter new book name") })


        Button(
            onClick = {
                val newBook = BookEntity(bookId!!.toInt(), input)
                viewModel.updateBook(newBook)

                navController.navigate("MainScreen")
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Update Book Name")
        }
    }
}