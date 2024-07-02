package com.epikron.objectstore.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.epikron.countriesandflags.ui.theme.Space
import com.epikron.objectstore.R
import com.epikron.objectstore.ui.theme.ObjectStoreTheme

@Composable
fun FloatingButton(
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = Modifier.padding(end = Space.huge),
        onClick = { onClick() },
        shape = CircleShape,
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary
    ) {
        Icon(
            modifier = Modifier.size(Space.almostHuge),
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.floating_button_hint)
        )
    }
}

@Preview
@Composable
fun PreviewFloatingButton() {
    ObjectStoreTheme {
        FloatingButton {}
    }
}