package com.mendhie.quicknotes.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Note
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.mendhie.quicknotes.R
import com.mendhie.quicknotes.data.di.AuthModule
import com.mendhie.quicknotes.data.models.Note
import com.mendhie.quicknotes.presentation.theme.QuickNotesTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onLogoutClick: () -> Unit, onCreateNoteClick: () -> Unit){
    //val allNotes = Utils.notes.toList()
    val categories = listOf("All", "Work", "Personal", "Reading", "Todo")

    val darkGray = Color(0xFF1E1E1E)
    val lightGray = Color(0xFF2A2A2A)

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var allNotes by remember {
        mutableStateOf(listOf<Note>())
    }
    var username by remember { mutableStateOf("") }

    AuthModule.userUseCases.getUserData(
        userId = AuthModule.authenticationUseCases.getCurrentUser()?.uid ?: "",
        onResult = { result ->
            result.onSuccess {
                username = it.firstName
            }
            result.onFailure {
                Log.d("Mega", "HomeScreen: ${it.localizedMessage}")
        }
        }
    )

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.LightGray),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Hi $username",
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Open menu */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.DarkGray)
                    }
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                    ) {
                        Icon(Icons.Default.Logout,
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxSize()
                                .size(80.dp)
                                .clickable {
                                    coroutineScope.launch {
                                        Toast
                                            .makeText(
                                                context,
                                                "Logging out...",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                        AuthModule.authenticationUseCases.signOut()
                                        onLogoutClick()
                                    }
                                },
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            Box(modifier = Modifier.padding(16.dp)){
                FloatingActionButton(
                    onClick = { onCreateNoteClick() },
                    containerColor = MaterialTheme.colorScheme.primary,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add Note",
                        tint = Color.White
                    )
                }
            }
        }
    ) { innerPadding ->

        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(6.dp)) {
            Text(
                text = "My Notes",
                fontSize = 46.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(4.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn{
                items(allNotes){note ->
                    NoteCard(note)
                }
            }

            AuthModule.noteUseCases.getNotes(
                userId = FirebaseAuth.getInstance().currentUser?.uid!!
            ){result->
                result.onSuccess {
                    allNotes = it
                }
                result.onFailure {
                    Log.d("Mega", "HomeScreen: ${it.localizedMessage}")
                }
            }
        }
    }

}

@Composable
fun NoteCard(note: Note, modifier: Modifier = Modifier){
    val context = LocalContext.current

    val backgroundColor = remember {
        listOf(
            Color(0xFFFFF9C4), // Light Yellow
            Color(0xFFE1BEE7), // Light Purple
            Color(0xFFBBDEFB), // Light Blue
            Color(0xFFC8E6C9), // Light Green
            Color(0xFFFFCCBC)  // Light Orange
        ).random()
    }

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable {
                Toast
                    .makeText(context, note.title, Toast.LENGTH_SHORT)
                    .show()
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Greeting(note = note, modifier = modifier)
    }
}

@Composable
fun Greeting(note: Note, modifier: Modifier = Modifier) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Note,
            contentDescription = null,
            modifier = Modifier.size(26.dp))

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = note.title,
            modifier = modifier.padding(4.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }

    Text(text = note.content,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QuickNotesTheme {
        Column {
            Text(
                text = "My Noteskjcjcjcj",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.padding(6.dp)) {
                item(8){
                    NoteCard(note = Note("jdjd", "ddd", "dd", System.currentTimeMillis()))
                }
            }
        }
    }
}