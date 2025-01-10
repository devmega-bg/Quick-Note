package com.mendhie.quicknotes.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mendhie.quicknotes.presentation.navigation.NoteNavigation
import com.mendhie.quicknotes.presentation.theme.QuickNotesTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickNotesTheme {
                NoteNavigation()
            }
        }
    }
}