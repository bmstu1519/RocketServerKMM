package org.rocketserverkmm.project.platform

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.rocketserverkmm.project.presentation.states.ActionableAlert

@Composable
actual fun AlertDialog(
    modifier: Modifier,
    alert: ActionableAlert,
    onDismissRequest: () -> Unit
) {
    ComposeAlertDialog(modifier, alert, onDismissRequest)
}

@Composable
fun ComposeAlertDialog(modifier: Modifier, alert: ActionableAlert, onDismissRequest: () -> Unit) {

    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            //Called when the user tries to dismiss the Dialog by clicking outside or pressing the back button
            onDismissRequest()
        },
        confirmButton = {
            TextButton(onClick = {
                alert.submitButton.action()
                onDismissRequest()
            }) {
                alert.submitButton.buttonText?.let { text ->
                    Text(text)
                }
            }
        },
        dismissButton = {
            TextButton(onClick = {
                alert.cancelButton.action()
                onDismissRequest()
            }) {
                alert.cancelButton.buttonText?.let { text ->
                    Text(text)
                }
            }
        },
        title = {
            Text(text = alert.text)
        },
        text = {
            Text(text = alert.text)
        },
    )
}