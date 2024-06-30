package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.navigation.Navigation
import com.example.expensetracker.presentation.HomeScreen
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import com.example.expensetracker.viewmodel.HomeViewModelFactory
import com.example.expensetracker.viewmodel.Homeviewmodel
import com.example.expensetracker.viewmodel.UserViewModelFactory
import com.example.expensetracker.viewmodel.Userviewmodel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private val auth: FirebaseAuth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val userViewModelFactory = UserViewModelFactory(applicationContext)
                    val userviewmodel = ViewModelProvider(this, userViewModelFactory)[Userviewmodel::class.java]
                    val viewModel  = HomeViewModelFactory(LocalContext.current).create(Homeviewmodel::class.java)
                    Navigation(auth,viewModel,userviewmodel)
                }
            }
        }
    }
}

