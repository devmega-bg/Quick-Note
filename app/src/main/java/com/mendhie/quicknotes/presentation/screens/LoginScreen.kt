package com.mendhie.quicknotes.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mendhie.quicknotes.data.di.AuthModule
import com.mendhie.quicknotes.data.models.User
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onSignupClick: () -> Unit,
    onLoginError: (String) -> Unit
){
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val leadingIconEmail = @Composable {
        Icon(
            Icons.Default.Email,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }

    val leadingIconPassword = @Composable {
        Icon(
            Icons.Default.Lock,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }

    val trailingIcon = @Composable {
        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
            Icon(
                if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "Welcome back! Glad to see you, Again!",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {email = it },
            leadingIcon = leadingIconEmail,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            leadingIcon = leadingIconPassword,
            trailingIcon = trailingIcon,
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(6.dp))
        
        Text(text = "Forgot password?", fontSize = 12.sp, modifier = Modifier.align(Alignment.End))

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                isLoading = true

                coroutineScope.launch {
                    val result = AuthModule.authenticationUseCases.signIn(email, password)
                    result.onSuccess {
                        isLoading = false
                        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                        onLoginSuccess()
                    }
                    result.onFailure {
                        isLoading = false
                        Toast.makeText(context, "Login Failed: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            enabled = email.isNotEmpty() && password.isNotEmpty() && !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
            } else {
                Text("Login")
            }
        }

        Spacer(modifier = Modifier.height(86.dp))

        Text(
            text = "Don't have an account? Register Now",
            fontSize = 14.sp,
            color = Color.Blue,
            modifier = Modifier.clickable {
                onSignupClick()
            }
        )

    }
}