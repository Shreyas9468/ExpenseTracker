package com.example.expensetracker.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.R
import com.example.expensetracker.data.model.ExpenseEntity
import com.example.expensetracker.ui.TextView
import com.example.expensetracker.viewmodel.HomeViewModelFactory
import com.example.expensetracker.viewmodel.Homeviewmodel
import com.example.expensetracker.viewmodel.Userviewmodel
import kotlinx.coroutines.delay
import java.util.Calendar

@Composable
fun HomeScreen(navController: NavController, viewModel: Homeviewmodel, userviewmodel: Userviewmodel) {
    val state = viewModel.expenses.collectAsState(initial = emptyList())
    val userInfo by userviewmodel.user.collectAsState(initial = null)

    var greeting by remember { mutableStateOf(getCurrentGreeting()) }

    LaunchedEffect(Unit) {
        while (true) {
            greeting = getCurrentGreeting()
            delay(60000)
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (nameRow, list, card, topBar, add) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.ic_topbar),
                contentDescription = null,
                modifier = Modifier.constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 64.dp, start = 16.dp, end = 16.dp)
                    .constrainAs(nameRow) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                Column {
                    Text(text = greeting, fontSize = 16.sp, color = Color.White)
                    Text(
                        text = (userInfo?.name ?: "User"),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }

            val expense = viewModel.getTotalExpense(state.value)
            val income = viewModel.getTotalIncome(state.value)
            val balance = viewModel.getBalance(state.value)

            cardItem(
                modifier = Modifier.constrainAs(card) {
                    top.linkTo(nameRow.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                expense, income, balance
            )

            TransactionList(
                modifier = Modifier.constrainAs(list) {
                    top.linkTo(card.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                },
                list = state.value,
                viewModel = viewModel
            )

            Box(
                modifier = Modifier
                    .constrainAs(add) {
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                    }
                    .shadow(8.dp, CircleShape)
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF6200EA))
                    .clickable { navController.navigate("/addExpenseScreen") },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = android.R.drawable.ic_menu_add),
                    contentDescription = "Add Expense",
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}

fun getCurrentGreeting(): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when (hour) {
        in 0..11 -> "Good Morning"
        in 12..17 -> "Good Afternoon"
        else -> "Good Evening"
    }
}

@Composable
fun TransactionList(modifier: Modifier, list: List<ExpenseEntity>, viewModel: Homeviewmodel) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                TextView(text = "Recent Transactions", fontSize = 20.sp)
                TextView(
                    text = "See all",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable { /* TODO: Navigate to all transactions screen */ },
                    color = Color.Gray
                )
            }
        }
        items(list) {
            TransactionItem(
                title = it.title,
                icon = viewModel.getItemIcon(it),
                date = it.date,
                amount = it.amount.toString(),
                color = if (it.type == "Income") Color.Green else Color.Red
            )
        }
    }
}

@Composable
fun TransactionItem(title: String, icon: Int, date: String, amount: String, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column {
                TextView(text = title, fontSize = 16.sp)
                TextView(text = date, fontSize = 12.sp)
            }
        }
        TextView(
            text = amount,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterEnd),
            color = color
        )
    }
}

@Composable
fun cardItem(modifier: Modifier, expense: String, income: String, balance: String) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = colorResource(id = R.color.Zinc))
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(modifier = Modifier.align(Alignment.CenterStart)) {
                TextView(text = "Total Balance", fontSize = 16.sp, color = Color.White)
                TextView(
                    text = balance,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Image(
                painter = painterResource(id = R.drawable.dots_menu),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            RowItem(
                modifier = Modifier.align(Alignment.CenterStart),
                title = "Income",
                img = R.drawable.ic_income,
                amount = income
            )
            RowItem(
                modifier = Modifier.align(Alignment.CenterEnd),
                title = "Expense",
                img = R.drawable.ic_expense,
                amount = expense
            )
        }
    }
}

@Composable
fun RowItem(modifier: Modifier, title: String, img: Int, amount: String) {
    Column(modifier = modifier) {
        Row {
            Image(painter = painterResource(id = img), contentDescription = null)
            Spacer(modifier = modifier.size(8.dp))
            TextView(title, fontSize = 16.sp, color = Color.White)
        }
        TextView(text = amount, fontSize = 20.sp, color = Color.White)
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(
        navController = rememberNavController(),
        viewModel = HomeViewModelFactory(LocalContext.current).create(Homeviewmodel::class.java),
        userviewmodel = HomeViewModelFactory(LocalContext.current).create(Userviewmodel::class.java)
    )
}
