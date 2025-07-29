package com.st11.eventmarker.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.st11.eventmarker.R
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.CalendarDay
import compose.icons.fontawesomeicons.solid.Clock




import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

import java.util.Locale



@Composable
fun DatePickerField(label: String, onDateSelected: (String) -> Unit) {
//    var selectedDate by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
//    val context = LocalContext.current
    val backgroundColor = colorResource(id = R.color.seina)

//    val calendar = Calendar.getInstance()
//    val year = calendar.get(Calendar.YEAR)
//    val month = calendar.get(Calendar.MONTH)
//    val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//    val datePickerDialog = android.app.DatePickerDialog(
//        context,
//        { _, y, m, d ->
//            val formattedDate = "$d/${m + 1}/$y"
//            selectedDate = formattedDate
//            onDateSelected(formattedDate)
//        },
//        year, month, day
//    )

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    fun showDatePicker() {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, day)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                date = dateFormat.format(selectedDate.time) // Format date to "YYYY-MM-DD"
                onDateSelected(date)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }


        OutlinedTextField(
            value = date,
            onValueChange = {},
//            enabled = , // ✅ prevents internal click behavior
            label = { Text(label) },
            trailingIcon = {
                Icon(
                    imageVector = FontAwesomeIcons.Solid.CalendarDay,
                    contentDescription = "date",
                    tint = colorResource(id = R.color.seina),
                    modifier = Modifier.size(24.dp)
//                        .clickable { datePickerDialog.show() }
                        .clickable { showDatePicker() }
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                focusedContainerColor = Color.White.copy(alpha = 0.95f),
                focusedBorderColor = backgroundColor,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = backgroundColor,
                cursorColor = backgroundColor
            )
        )
    }


