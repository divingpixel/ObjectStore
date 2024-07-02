package com.epikron.objectstore.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.epikron.countriesandflags.ui.theme.Space
import com.epikron.data.models.ObjectModel
import com.epikron.objectstore.ui.theme.ObjectStoreTheme
import com.epikron.objectstore.ui.theme.mainTypography

@Composable
fun ObjectSelector(
    modifier: Modifier = Modifier,
    objectModel: ObjectModel,
    isSelected: Boolean,
    onSelect: (Int) -> Unit,
) {
    Box(
        modifier = modifier.then(
            Modifier
                .clickable { onSelect.invoke(objectModel.id) }
                .padding(bottom = Space.extraSmall)
                .background(
                    if (isSelected)
                        MaterialTheme.colorScheme.onSecondary
                    else
                        MaterialTheme.colorScheme.onPrimary
                )
                .height(IntrinsicSize.Max)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(Space.extraSmall)
                .fillMaxWidth(),
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
    }
}

@Preview
@Composable
fun PreviewObjectSelector() {
    ObjectStoreTheme {
        Column {
            ObjectSelector(
                objectModel = ObjectModel(0, "R2D2", "All purpose beeping robot", "Robot"),
                isSelected = true,
                onSelect = {}
            )
            ObjectSelector(
                objectModel = ObjectModel(0, "R2D2", "All purpose beeping robot", "Robot"),
                isSelected = false,
                onSelect = {}
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun PreviewObjectSelectorDark() {
    ObjectStoreTheme {
        Column {
            ObjectSelector(
                objectModel = ObjectModel(0, "R2D2", "All purpose beeping robot", "Robot"),
                isSelected = true,
                onSelect = {}
            )
            ObjectSelector(
                objectModel = ObjectModel(0, "R2D2", "All purpose beeping robot", "Robot"),
                isSelected = false,
                onSelect = {}
            )
        }
    }
}