package com.mendhie.quicknotes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.mendhie.quicknotes.presentation.screens.CreateNoteScreen
import com.mendhie.quicknotes.presentation.screens.HomeScreen
import com.mendhie.quicknotes.presentation.screens.LoginScreen
import com.mendhie.quicknotes.presentation.screens.SignupScreen

enum class AppScreen(val screen: String){
    LoginScreen("login"),
    SignupScreen("signup"),
    HomeScreen("home"),
    CreateNoteScreen("newNote"),
    //NoteDetailScreen("noteDetail")
}

@Composable
fun NoteNavigation(){
    val navController = rememberNavController()
    var loggedIn by remember { mutableStateOf(FirebaseAuth.getInstance().currentUser != null)}
    val startDestination = if(loggedIn)AppScreen.HomeScreen.screen else AppScreen.LoginScreen.screen

    FirebaseAuth.getInstance().addAuthStateListener { auth->
        loggedIn = (auth.currentUser!=null)
    }
    NavHost(
        navController = navController,
        startDestination = startDestination){
        composable(route = AppScreen.HomeScreen.screen){
            HomeScreen(
                onLogoutClick = {
                //navController.navigate(AppScreen.LoginScreen.screen)
            }
            ){
                navController.navigate(AppScreen.CreateNoteScreen.screen)
            }
        }

        composable(route = AppScreen.LoginScreen.screen){
            LoginScreen(
                onLoginSuccess = { navController.navigate(AppScreen.HomeScreen.screen) },
                onSignupClick = { navController.navigate(AppScreen.SignupScreen.screen)},
                onLoginError = { errorMessage ->
                    // Handle the error
                    println("Login error: $errorMessage")
                }
            )
        }

        composable(route = AppScreen.SignupScreen.screen){
            SignupScreen(
                onSignupSuccess = { navController.navigate(AppScreen.HomeScreen.screen) },
                onSignupError = { errorMessage ->
                    // Handle the error
                    println("Login error: $errorMessage")
                }
            )
        }

        composable(route = AppScreen.CreateNoteScreen.screen){
            CreateNoteScreen(
                onSave = {
                    navController.navigateUp()
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}