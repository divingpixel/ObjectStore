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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.epikron.countriesandflags.ui.theme.Space
import com.epikron.objectstore.R
import com.epikron.objectstore.ui.models.MainScreenState

@Composable
fun SearchField(
    uiState: MainScreenState,
    onValueChange: (String) -> Unit,
    onSearchFocusChanged: (Boolean) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val searchFieldElementsColor = MaterialTheme.colorScheme.primary
    val searchFieldBackgroundColor = MaterialTheme.colorScheme.onPrimary

    TextField(
        modifier = Modifier
            .padding(Space.medium)
            .fillMaxWidth()
            .onFocusChanged { onSearchFocusChanged.invoke(it.isFocused) },
        singleLine = true,
        value = if (uiState.isSearchActive.not() && uiState.searchText.isEmpty())
            stringResource(id = R.string.search) else uiState.searchText,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = searchFieldBackgroundColor,
            unfocusedContainerColor = searchFieldBackgroundColor,
            focusedTextColor = searchFieldElementsColor,
            unfocusedTextColor = searchFieldElementsColor.copy(if (uiState.searchText.isEmpty()) 0.5f else 1f),
            focusedIndicatorColor = searchFieldElementsColor,
            unfocusedIndicatorColor = searchFieldElementsColor
        ),
        trailingIcon = {
            if (uiState.searchText.isNotEmpty())
                Icon(
                    modifier = Modifier.clickable { onValueChange.invoke("") },
                    imageVector = Icons.Outlined.Clear,
                    tint = searchFieldElementsColor,
                    contentDescription = ""
                )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { focusManager.clearFocus() }
        )
    )
}
