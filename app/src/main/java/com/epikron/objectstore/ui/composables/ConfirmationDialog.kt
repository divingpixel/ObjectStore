package com.epikron.objectstore.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.epikron.countriesandflags.ui.theme.Shapes
import com.epikron.countriesandflags.ui.theme.Space
import com.epikron.objectstore.R
import com.epikron.objectstore.ui.theme.mainTypography

@Composable
fun ConfirmationDialog(
    message: String,
    onActionResult: (Boolean) -> Unit
) {
    Dialog(onDismissRequest = { onActionResult.invoke(false) }) {
        val mainColor = MaterialTheme.colorScheme.onSurface
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Space.medium),
            shape = Shapes.medium,
            colors = CardDefaults.cardColors().copy(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = mainColor
            )
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    style = mainTypography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(Space.medium, Space.medium),
                    textAlign = TextAlign.Center
                )
                HorizontalDivider(modifier = Modifier
                    .padding(Space.small, Space.none)
                    .weight(0.1f, false), color = mainColor)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Space.medium)
                        .weight(1f, false),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    ElevatedButton(
                        onClick = { onActionResult.invoke(true) },
                        modifier = Modifier.padding(Space.none, Space.small, Space.none, Space.small),
                        colors = ButtonDefaults.textButtonColors().copy(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.yes),
                            style = mainTypography.displayMedium
                        )
                    }
                    ElevatedButton(
                        onClick = { onActionResult.invoke(false) },
                        modifier = Modifier.padding(Space.none, Space.small, Space.none, Space.small),
                        colors = ButtonDefaults.textButtonColors().copy(
                            containerColor = MaterialTheme.colorScheme.onSurface,
                            contentColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.no),
                            style = mainTypography.displayMedium
                        )
                    }
                }
            }
        }
    }
}
