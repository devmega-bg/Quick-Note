package com.mendhie.quicknotes.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mendhie.quicknotes.data.di.AuthModule
import com.mendhie.quicknotes.data.models.Note
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteScreen(onSave: () -> Unit, onBackClick: () -> Unit){

    var title by remember{ mutableStateOf("") }
    var content by remember{ mutableStateOf("") }
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.LightGray),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "",
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Back", tint = Color.DarkGray)
                    }
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                    ) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Default.Add, contentDescription = "Attach", tint = Color.DarkGray)
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            Box(modifier = Modifier.padding(16.dp)){
                FloatingActionButton(
                    onClick = {
                        if(title.isNotEmpty() && content.isNotEmpty()){
                            Toast.makeText(context, "Saving...", Toast.LENGTH_SHORT).show()
                            val note = Note(title = title, content = content)

                            coroutineScope.launch {
                                val result = AuthModule.noteUseCases.createNote(note)
                                result.onSuccess {
                                    Toast.makeText(context, "Note Saved", Toast.LENGTH_SHORT).show()
                                    onSave()
                                }
                                result.onFailure {
                                    Toast.makeText(context, "Error: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
                                }

                            }
                        }

                              },
                    containerColor = Color.DarkGray,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = "Add Note",
                        tint = Color.White
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TextField(
                value = title,
                onValueChange ={ title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = content,
                onValueChange ={ content = it },
                label = { Text("Content") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
        }
    }
}