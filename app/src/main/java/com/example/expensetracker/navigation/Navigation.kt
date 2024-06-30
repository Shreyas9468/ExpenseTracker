package com.example.expensetracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.presentation.AddExpense
import com.example.expensetracker.presentation.HomeScreen
import com.example.expensetracker.presentation.RegistrationScreen
import com.example.expensetracker.viewmodel.Homeviewmodel
import com.example.expensetracker.viewmodel.Userviewmodel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Navigation(auth: FirebaseAuth , viewModel : Homeviewmodel , userviewmodel: Userviewmodel){

    val navController = rememberNavController()

    val startDestination= if (auth.currentUser != null) "/homescreen" else "/registration"

        NavHost(navController = navController, startDestination = startDestination) {
                composable("/homescreen"){

                    HomeScreen(navController ,viewModel , userviewmodel)

                }

                composable("/addExpenseScreen"){
                    AddExpense(navController)
                }

                composable("/registration"){
                    RegistrationScreen(viewModel = userviewmodel, navController = navController, auth =auth )
                }




        }

}
