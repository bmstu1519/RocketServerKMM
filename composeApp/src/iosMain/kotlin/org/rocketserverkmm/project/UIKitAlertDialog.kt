package org.rocketserverkmm.project

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import org.rocketserverkmm.project.presentation.states.ActionableAlert
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleCancel
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication

@OptIn(ExperimentalForeignApi::class)
@Composable
fun UIKitAlertDialog(
    modifier: Modifier = Modifier,
    alertDialog: ActionableAlert,
    onDismissRequest: () -> Unit
) {

    UIKitView(
        modifier = modifier,
        factory = {

            val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController

            val alert = UIAlertController.alertControllerWithTitle(
                title = alertDialog.text,
                message = alertDialog.text,
                preferredStyle = UIAlertControllerStyleAlert
            )

            val submitAction =
                UIAlertAction.actionWithTitle(
                    alertDialog.submitButton.buttonText,
                    style = UIAlertActionStyleDefault,
                    handler = {
                        alertDialog.submitButton.action()
                        alert.dismissViewControllerAnimated(flag = true, completion = null)
                        onDismissRequest()
                    })

            val cancelAction = UIAlertAction.actionWithTitle(
                alertDialog.cancelButton.buttonText,
                style = UIAlertActionStyleCancel,
                handler = {
                    alertDialog.cancelButton.action()
                    alert.dismissViewControllerAnimated(flag = true, completion = null)
                    onDismissRequest()
                })

            alert.addAction(submitAction)
            alert.addAction(cancelAction)
            rootViewController?.presentViewController(alert, animated = true, completion = null)

            alert.view
        },
        update = {

        }
    )
}