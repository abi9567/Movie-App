package com.example.movieapp.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import com.example.movieapp.ui.theme.SecondaryLightColor
import com.example.movieapp.ui.theme.TextFieldBorderColor

@Composable
fun CustomTextBoxView(
    modifier : Modifier = Modifier,
    value : String?,
    onValueChane : (String?) -> Unit,
    placeHolderText : String?
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(value = value ?: "",
        modifier = modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.bodyLarge,
        shape = MaterialTheme.shapes.medium,
        placeholder = {
            Text(text = placeHolderText ?: "",
                maxLines = 1,
                color = SecondaryLightColor,
                style = MaterialTheme.typography.bodySmall)
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            focusedBorderColor = TextFieldBorderColor,
            unfocusedBorderColor = TextFieldBorderColor,
            unfocusedContainerColor = Color.Transparent
        ),
        onValueChange = onValueChane)
}