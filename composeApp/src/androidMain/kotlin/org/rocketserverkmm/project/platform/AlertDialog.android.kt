package org.rocketserverkmm.project.platform

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun AlertDialog(
    modifier: Modifier,
    onDismissRequest: () -> Unit
) {
    ComposeAlertDialog(modifier, onDismissRequest)
}

@Composable
fun ComposeAlertDialog(modifier: Modifier, onDismissRequest: () -> Unit) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            //Called when the user tries to dismiss the Dialog by clicking outside or pressing the back button
            onDismissRequest()
        },
        confirmButton = {
            TextButton(onClick = {
                onDismissRequest()
            }) {
                Text("Ok")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismissRequest()
            }) {
                Text("Cancel")
            }
        },
        title = {
            Text(text = "Compose Alert")
        },
        text = {
            Text(text = "I am Pure Compose Alert")
        },
    )
}