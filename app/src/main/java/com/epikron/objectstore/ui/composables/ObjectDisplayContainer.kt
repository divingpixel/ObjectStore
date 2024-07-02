package com.epikron.objectstore.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.epikron.countriesandflags.ui.theme.Space
import com.epikron.data.models.ObjectModel
import com.epikron.objectstore.R
import com.epikron.objectstore.ui.theme.ObjectStoreTheme
import com.epikron.objectstore.ui.theme.mainTypography

@Composable
fun ObjectMenu(
    isExpanded: Boolean,
    editAction: () -> Unit,
    deleteAction: () -> Unit,
    onDismiss: () -> Unit,
) {
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = onDismiss
    ) {

        DropdownMenuItem(
            onClick = editAction,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = stringResource(id = R.string.edit)
                )
            },
            text = {
                Text(
                    modifier = Modifier.padding(end = Space.extraLarge),
                    text = stringResource(id = R.string.edit)
                )
            }
        )
        HorizontalDivider(modifier = Modifier.padding(Space.small), color = MaterialTheme.colorScheme.onSurface)
        DropdownMenuItem(
            onClick = deleteAction,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = stringResource(id = R.string.delete)
                )
            },
            text = {
                Text(
                    modifier = Modifier.padding(end = Space.extraLarge),
                    text = stringResource(id = R.string.delete)
                )
            }
        )
    }
}

@Composable
fun ObjectDisplayContainer(
    modifier: Modifier = Modifier,
    objectModel: ObjectModel,
    editAction: (Int) -> Unit,
    deleteAction: (Int) -> Unit
) {
    var isMenuOpen by remember { mutableStateOf(false) }
    Box(
        modifier = modifier.then(
            Modifier
                .padding(bottom = Space.small)
                .background(MaterialTheme.colorScheme.onPrimary)
                .height(IntrinsicSize.Max)
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(Space.small),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${objectModel.type}: ${objectModel.name}",
                    style = mainTypography.displayMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = objectModel.description,
                    style = mainTypography.displayMedium,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(
                onClick = { isMenuOpen = true }
            ) {
                ObjectMenu(
                    isExpanded = isMenuOpen,
                    editAction = {
                        isMenuOpen = false
                        editAction.invoke(objectModel.id)
                    },
                    deleteAction = {
                        isMenuOpen = false
                        deleteAction.invoke(objectModel.id)
                    },
                    onDismiss = {
                        isMenuOpen = false
                    }
                )
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = stringResource(id = R.string.object_options)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewObjectDisplayContainer() {
    ObjectStoreTheme {
        ObjectDisplayContainer(
            objectModel = ObjectModel(0, "R2D2", "All purpose beeping robot", "Robot"),
            editAction = {},
            deleteAction = {}
        )
    }
}