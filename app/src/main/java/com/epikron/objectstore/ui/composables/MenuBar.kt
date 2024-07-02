package com.epikron.objectstore.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.epikron.objectstore.R
import com.epikron.objectstore.ui.theme.ObjectStoreTheme
import com.epikron.objectstore.ui.theme.mainTypography

@Composable
fun MenuBar(
    modifier: Modifier = Modifier,
    title: String,
    menuAction: (() -> Unit)? = null,
    backAction: (() -> Unit)? = null
) {
    Box(
        modifier = modifier.then(
            Modifier.background(MaterialTheme.colorScheme.onPrimary)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                modifier = Modifier,
                onClick = { backAction?.invoke() }
            ) {
                val alpha: Float = backAction?.let { 1f } ?: 0f
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    tint = MaterialTheme.colorScheme.primary.copy(alpha),
                    contentDescription = stringResource(id = R.string.back)
                )
            }
            Text(
                modifier = Modifier
                    .weight(1f),
                text = title,
                style = mainTypography.titleSmall,
                color = MaterialTheme.colorScheme.primaryContainer,
                textAlign = TextAlign.Center
            )
            IconButton(
                onClick = { menuAction?.invoke() }
            ) {
                val alpha: Float = menuAction?.let { 1f } ?: 0f
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.save),
                    tint = MaterialTheme.colorScheme.primary.copy(alpha),
                    contentDescription = stringResource(id = R.string.save)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewMenuBar() {
    ObjectStoreTheme {
        MenuBar(title = "Label", menuAction = {})
    }
}