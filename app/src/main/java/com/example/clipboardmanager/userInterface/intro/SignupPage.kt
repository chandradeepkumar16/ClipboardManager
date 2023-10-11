package com.example.clipboardmanager.userInterface.intro

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.clipboardmanager.R
import com.example.clipboardmanager.data.User
import com.example.clipboardmanager.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

//@Preview
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavController) {

    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    // Create FocusRequesters for the email, password, and confirmPassword fields
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val confirmPasswordFocusRequester = remember { FocusRequester() }

    val emailRegex = Regex("^\\S+@\\S+\\.\\S+$")

    val firebaseAuth:FirebaseAuth=FirebaseAuth.getInstance()
    fun isValidEmailFormat(email: String): Boolean {
        return emailRegex.matches(email)
    }


    val density = LocalDensity.current.density
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {

        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFFADD8E6), Color(0xFF90EE90)), // Light blue to light green
                        startY = 0f,
                        endY = 400 * density
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App Name and Icon
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_clip), // Replace with your app icon
                    contentDescription = null, // Set appropriate content description
                    modifier = Modifier.size(48.dp)
                )

                Text(
                    text = "ClipBoard Manager",
                    style = MaterialTheme.typography.headlineSmall,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }

            // Email TextField with hint text
            Column(
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Email") // Hint text
                BasicTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        isButtonEnabled = isValidSignupInput(email, password, confirmPassword)
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            // Request focus for the password field
                            emailFocusRequester.requestFocus()
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp)
                        .focusRequester(emailFocusRequester)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Password TextField with hint text
            Column(
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Password") // Hint text
                BasicTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        isButtonEnabled = isValidSignupInput(email, password, confirmPassword)
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),

                    keyboardActions = KeyboardActions(
                        onNext = {
                            // Request focus for the confirmPassword field
                            passwordFocusRequester.requestFocus()
                        }
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp)
                        .focusRequester(passwordFocusRequester)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Password TextField with hint text
            Column(
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Confirm Password") // Hint text
                BasicTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        isButtonEnabled = isValidEmailFormat(email) && isValidSignupInput(email, password, confirmPassword)
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            // Hide the keyboard when 'Done' is pressed
                            keyboardController?.hide()
                        }
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp)
                        .focusRequester(confirmPasswordFocusRequester)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Signup Button
            Button(
                onClick = {

                 firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                     if(it.isSuccessful){

                         val currentUser = FirebaseAuth.getInstance().currentUser

                         if (currentUser != null) {
                             val user = User(currentUser.uid, email, password)
                             saveUserDataToDatabase(user)
                         }

                         Toast.makeText(context, "SignUp Successfully", Toast.LENGTH_SHORT).show()
                         navController.navigate(route = AppScreens.ClipboardManagerApp.name)

                     }else{
                         Toast.makeText(context, "SignUp Failed", Toast.LENGTH_SHORT).show()
                     }
                 }

                },
                enabled = isButtonEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Signup")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Register here text
            Text(
                text = "Login here",
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.navigate(AppScreens.LoginPage.name)
                }
            )


        }
    }
}


private fun saveUserDataToDatabase(user: User) {
    val database = FirebaseDatabase.getInstance()
    val usersRef = database.getReference("users")
    // Use the user's UID as the key in the database
    usersRef.child(user.uid).child("credentials").setValue(user)

}

private fun isValidSignupInput(email: String, password: String, confirmPassword: String): Boolean {
    return email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank() && password == confirmPassword
}


