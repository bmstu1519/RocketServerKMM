package org.rocketserverkmm.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
expect fun AlertDialog(
    modifier: Modifier,
    onDismissRequest: () -> Unit
)