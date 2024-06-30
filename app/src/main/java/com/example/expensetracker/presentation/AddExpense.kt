package com.example.expensetracker.presentation

import android.widget.Toast
//import androidx.compose.material3.icons.filled.CalendarToday
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.R
import com.example.expensetracker.data.model.ExpenseEntity
import com.example.expensetracker.viewmodel.AddExpenseviewmodel
import com.example.expensetracker.viewmodel.AddExpenseviewmodelFactory
import kotlinx.coroutines.launch

@Composable
fun AddExpense(navController: NavController) {
    val viewModel =
        AddExpenseviewmodelFactory(LocalContext.current).create(AddExpenseviewmodel::class.java)

    val coroutineScope = rememberCoroutineScope()
    Surface(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (nameRow, list, card, topBar) = createRefs()
            Image(
                painter = painterResource(id = R.drawable.ic_topbar),
                contentDescription = null,
                modifier = Modifier.constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

            Box(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(nameRow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(top = 60.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = "Add Expense", fontSize = 24.sp, fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    color = Color.White,
                )
                Image(
                    painter = painterResource(id = R.drawable.dots_menu), contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_back), contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterStart).clickable {
                        navController.popBackStack()
                    }
                )
            }
            DataForm(modifier = Modifier
                .padding(top = 60.dp)
                .constrainAs(card) {
                    top.linkTo(nameRow.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ) {
                coroutineScope.launch {
                    if (viewModel.InsertExpense(it)) {
                        navController.popBackStack()
                    } else {
                        // Handle error
                    }
                }
            }
        }
    }
}

@Composable
fun DataForm(modifier: Modifier, onAddExpenseClick: (model: ExpenseEntity) -> Unit) {
    val name = remember { mutableStateOf("") }
    val amount = remember { mutableStateOf("") }
    val date = remember { mutableLongStateOf(0L) }
    val dateDialogVisibility = remember { mutableStateOf(false) }
    val category = remember { mutableStateOf("") }
    val type = remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Name", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(16.dp))

        Text(text = "Amount", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            value = amount.value,
            onValueChange = { amount.value = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(16.dp))

        Text(text = "Date", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            value = if (date.value == 0L) "" else Utils.formatDatatoHumanReadableForm(date.value),
            onValueChange = { },
            placeholder = { Text(text = "Select Date") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { dateDialogVisibility.value = true },
            enabled = false,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "Date Picker",
                    modifier = Modifier.clickable { dateDialogVisibility.value = true }
                )
            }
        )
        Spacer(modifier = Modifier.size(16.dp))

        Text(text = "Category", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
        Spacer(modifier = Modifier.size(10.dp))
        ExpenseDropDown(
            listofItems = listOf("Netflix","Salary", "Paypal", "Starbucks", "Upwork"),
            onItemClicked = { category.value = it }
        )
        Spacer(modifier = Modifier.size(16.dp))

        Text(text = "Type", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
        Spacer(modifier = Modifier.size(10.dp))
        ExpenseDropDown(
            listofItems = listOf("Income", "Expense"),
            onItemClicked = { type.value = it }
        )
        Spacer(modifier = Modifier.size(16.dp))

        Button(
            onClick = {
                val expenseEntity = ExpenseEntity(
                    null,
                    name.value,
                    amount.value.toDoubleOrNull() ?: 0.0,
                    Utils.formatDatatoHumanReadableForm(date.value),
                    category.value,
                    type.value
                )
                onAddExpenseClick(expenseEntity)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5EA099))
        ) {
            Text(text = "Add Expense", color = Color.White)
        }
    }
    if (dateDialogVisibility.value) {
        ExpenseDatePicker(
            ondateselected = {
                date.value = it
                dateDialogVisibility.value = false
            },
            onDismiss = { dateDialogVisibility.value = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDatePicker(
    ondateselected: (date: Long) -> Unit,
    onDismiss: () -> Unit
) {
    val datepickerState = rememberDatePickerState()
    val selectDate = datepickerState.selectedDateMillis ?: 0L
    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = { ondateselected(selectDate) }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(state = datepickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDropDown(listofItems: List<String>, onItemClicked: (item: String) -> Unit) {
    val expand = remember { mutableStateOf(false) }
    val selecteditem = remember { mutableStateOf(listofItems[0]) }
    ExposedDropdownMenuBox(expanded = expand.value, onExpandedChange = {
        expand.value = it
    }) {
        TextField(
            value = selecteditem.value,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expand.value)
            }
        )
        ExposedDropdownMenu(expanded = expand.value, onDismissRequest = { }) {
            listofItems.forEach {
                DropdownMenuItem(
                    text = { Text(text = it) },
                    onClick = {
                        selecteditem.value = it
                        onItemClicked(selecteditem.value)
                        expand.value = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun preview() {
    AddExpense(rememberNavController())
}
