@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.app_31_libraryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app_31_libraryapp.repository.Repository
import com.example.app_31_libraryapp.room.BookDB
import com.example.app_31_libraryapp.room.BookEntity
import com.example.app_31_libraryapp.ui.theme.APP31_LibraryAppTheme
import com.example.app_31_libraryapp.viewModel.BookViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APP31_LibraryAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel =
                        BookViewModel(Repository(BookDB.getInstance(LocalContext.current)))

                    val navController = rememberNavController()

                    NavHost(navController, startDestination = "MainScreen") {
                        composable("MainScreen") {
                            MainScreen(viewModel = viewModel, navController)
                        }
                        composable("UpdateScreen/{bookId}") {
                            UpdateScreen(viewModel, bookId = it.arguments?.getString("bookId"), navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: BookViewModel, navController: NavHostController) {

    var inputBook by remember {
        mutableStateOf("")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 24.dp, start = 4.dp, end = 4.dp)
    ) {

        Text(text = "Insert Books Into Database", fontSize = 24.sp)

        OutlinedTextField(
            value = inputBook,
            onValueChange = { name -> inputBook = name },
            label = { Text(text = "Book Name") },
            placeholder = {
                Text(
                    text = "Please enter a book name"
                )
            })

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = { viewModel.addBook(BookEntity(0, inputBook)) },
            colors = ButtonDefaults.buttonColors(
                Color.Magenta
            )
        ) {
            Text(text = "Insert Book")
        }

        BooksList(viewModel, navController)
    }
}

@Composable
fun BookCard(viewModel: BookViewModel, book: BookEntity, navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${book.id}",
                fontSize = 24.sp,
                modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                color = Color.Blue
            )

            Text(
                text = book.title,
                fontSize = 24.sp,
                modifier = Modifier.fillMaxSize(0.7f),
                color = Color.Black
            )

            Row(horizontalArrangement = Arrangement.End) {
                IconButton(onClick = { viewModel.deleteBook(book) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }

                IconButton(onClick = { navController.navigate("UpdateScreen/${book.id}") }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                }
            }
        }
    }
}

@Composable
fun BooksList(viewModel: BookViewModel, navController: NavHostController) {
    val books by viewModel.books.collectAsState(initial = emptyList())

    Column(Modifier.padding(16.dp)) {

        Text(text = "My Library : ", fontSize = 20.sp, color = Color.Red)

        LazyColumn {
            items(books) { book ->
                BookCard(viewModel, book, navController)
            }
        }
    }
}

