package com.epikron.objectstore.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.epikron.countriesandflags.ui.theme.Shapes
import com.epikron.countriesandflags.ui.theme.Space
import com.epikron.data.models.ObjectModel
import com.epikron.objectstore.R
import com.epikron.objectstore.ui.models.RelationsPickerState
import com.epikron.objectstore.ui.theme.ObjectStoreTheme
import com.epikron.objectstore.ui.theme.mainTypography

@Composable
fun RelationObjectDialog(
    pickerState: RelationsPickerState,
    onSelect: (Int) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmAction: (Int) -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
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
                    modifier = Modifier.padding(Space.medium, Space.large, Space.medium, Space.small),
                    style = mainTypography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    text = stringResource(
                        id = if (pickerState.availableRelations.isNotEmpty())
                            R.string.pick_relation else R.string.pick_impossible
                    )
                )
                LazyColumn(
                    modifier = Modifier.padding(Space.small)
                ) {
                    items(pickerState.availableRelations) { objectModel ->
                        ObjectSelector(
                            objectModel = objectModel,
                            isSelected = objectModel.id == pickerState.selectedObjectId,
                            onSelect = onSelect
                        )
                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                        .padding(Space.small, Space.none)
                        .weight(0.1f, false), color = mainColor
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Space.medium)
                        .weight(1f, false),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    ElevatedButton(
                        onClick = { onConfirmAction(pickerState.selectedObjectId) },
                        modifier = Modifier.padding(Space.none, Space.small, Space.none, Space.small),
                        colors = ButtonDefaults.textButtonColors().copy(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.done),
                            style = mainTypography.displayMedium
                        )
                    }
                    ElevatedButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(Space.none, Space.small, Space.none, Space.small),
                        colors = ButtonDefaults.textButtonColors().copy(
                            containerColor = MaterialTheme.colorScheme.onSurface,
                            contentColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            style = mainTypography.displayMedium
                        )
                    }
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun PreviewRelationObjectDialog() {
    ObjectStoreTheme {
        RelationObjectDialog(
            pickerState = RelationsPickerState(
                shouldShowRelationsDialog = true,
                availableRelations = listOf(ObjectModel.preview),
                selectedObjectId = 0
            ),
            onSelect = {},
            onDismissRequest = {},
            onConfirmAction = {}
        )
    }
}
