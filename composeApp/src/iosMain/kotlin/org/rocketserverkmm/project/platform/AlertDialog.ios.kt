package org.rocketserverkmm.project.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.rocketserverkmm.project.UIKitAlertDialog

@Composable
actual fun AlertDialog(
    modifier: Modifier,
    onDismissRequest: () -> Unit
) {
    UIKitAlertDialog(modifier, onDismissRequest)
}