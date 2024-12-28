package org.rocketserverkmm.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.rocketserverkmm.project.presentation.states.ActionableAlert


@Composable
expect fun AlertDialog(
    modifier: Modifier,
    alert: ActionableAlert,
    onDismissRequest: () -> Unit
)