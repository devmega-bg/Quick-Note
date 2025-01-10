package com.mendhie.quicknotes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.mendhie.quicknotes.presentation.screens.CreateNoteScreen
import com.mendhie.quicknotes.presentation.screens.HomeScreen
import com.mendhie.quicknotes.presentation.screens.LoginScreen

enum class AppScreen(val screen: String){
    LoginScreen("login"),
    HomeScreen("home"),
    CreateNoteScreen("newNote"),
    //NoteDetailScreen("noteDetail")
}

@Composable
fun NoteNavigation(){
    val navController = rememberNavController()
    val startDestination = if(FirebaseAuth.getInstance().currentUser != null)AppScreen.HomeScreen.screen else AppScreen.LoginScreen.screen

    NavHost(
        navController = navController,
        startDestination = startDestination){

        composable(route = AppScreen.HomeScreen.screen){
            HomeScreen(onLogoutClick = {}){
                navController.navigate(AppScreen.CreateNoteScreen.screen)
            }
        }

        composable(route = AppScreen.LoginScreen.screen){
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(AppScreen.HomeScreen.screen)
                },
                onLoginError = { errorMessage ->
                    // Handle the error
                    println("Login error: $errorMessage")
                }
            )
        }

        composable(route = AppScreen.CreateNoteScreen.screen){
            CreateNoteScreen(
                onSave = {},
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}