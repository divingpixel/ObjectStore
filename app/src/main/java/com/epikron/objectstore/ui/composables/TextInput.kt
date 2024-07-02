package com.epikron.objectstore.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.epikron.countriesandflags.ui.theme.Space
import com.epikron.objectstore.ui.models.TextInputState

@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    uiState: TextInputState,
    label : String,
    errorText : String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val textFieldElementsColor = MaterialTheme.colorScheme.primary
    val textFieldBackgroundColor = MaterialTheme.colorScheme.onPrimary

    TextField(
        modifier = modifier.then(
            Modifier
                .padding(horizontal = Space.medium, vertical = Space.small)
                .fillMaxWidth()
                .onFocusChanged {
                    if (it.isFocused.not()) onDone.invoke()
                }
        ),
        singleLine = true,
        value = uiState.text,
        label = { Text(label) },
        isError = uiState.isError,
        supportingText = { Text(if (uiState.isError) errorText else "") },
        onValueChange = onValueChange,
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = textFieldBackgroundColor,
            unfocusedContainerColor = textFieldBackgroundColor,
            focusedTextColor = textFieldElementsColor,
            unfocusedTextColor = textFieldElementsColor,
            focusedLabelColor = textFieldElementsColor,
            unfocusedLabelColor = textFieldElementsColor.copy(0.5f),
            focusedIndicatorColor = textFieldElementsColor,
            unfocusedIndicatorColor = textFieldElementsColor
        ),
        trailingIcon = {
            if (uiState.text.isNotEmpty())
                Icon(
                    modifier = Modifier.clickable { onValueChange.invoke("") },
                    imageVector = Icons.Outlined.Clear,
                    tint = textFieldElementsColor,
                    contentDescription = ""
                )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                onDone.invoke()
            }
        )
    )
}
