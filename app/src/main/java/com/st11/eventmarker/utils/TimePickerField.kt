package com.st11.eventmarker.utils


import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.st11.eventmarker.R
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Clock
import java.util.Calendar
import java.util.Locale


@Composable
fun TimePickerField(label: String, onTimeSelected: (String) -> Unit) {
    var selectedTime by remember { mutableStateOf("") }
    val context = LocalContext.current
    val backgroundColor = colorResource(id = R.color.seina)

    Box(modifier = Modifier
        .fillMaxWidth()

    ) {
        OutlinedTextField(
            value = selectedTime,
            onValueChange = {},
            label = { Text(label) },
            trailingIcon = {
                Icon(
                    imageVector = FontAwesomeIcons.Solid.Clock,
                    contentDescription = "clock",
                    tint = colorResource(id = R.color.seina),
                    modifier = Modifier.size(24.dp)
                        .clickable {
                            val calendar = Calendar.getInstance()
                            val hour = calendar.get(Calendar.HOUR_OF_DAY)
                            val minute = calendar.get(Calendar.MINUTE)

                            TimePickerDialog(
                                context,
                                { _, h, m ->
                                    val formattedTime = String.format(Locale.getDefault(),"%02d:%02d", h, m)
                                    selectedTime = formattedTime
                                    onTimeSelected(formattedTime)
                                },
                                hour, minute, false
                            ).show()
                        }
                )
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                focusedContainerColor = Color.White.copy(alpha = 0.95f),
                focusedBorderColor = backgroundColor,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = backgroundColor,
                cursorColor = backgroundColor
            )
//                .clickable {
//                    val calendar = Calendar.getInstance()
//                    val hour = calendar.get(Calendar.HOUR_OF_DAY)
//                    val minute = calendar.get(Calendar.MINUTE)
//
//                    TimePickerDialog(
//                        context,
//                        { _, h, m ->
//                            val formattedTime = String.format("%02d:%02d", h, m)
//                            selectedTime = formattedTime
//                            onTimeSelected(formattedTime)
//                        },
//                        hour, minute, false
//                    ).show()
//                }
        )
    }
}



