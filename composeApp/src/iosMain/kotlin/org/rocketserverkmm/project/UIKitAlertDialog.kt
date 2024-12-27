package org.rocketserverkmm.project

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
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
    onDismissRequest: () -> Unit
) {

    UIKitView(
        modifier = modifier,
        factory = {

            val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController

            val alert = UIAlertController.alertControllerWithTitle(
                title = "UIKit Alert Dialog",
                message = "Im UIKit Alert Dialog Shown In Compose UI",
                preferredStyle = UIAlertControllerStyleAlert
            )

            val action =
                UIAlertAction.actionWithTitle("OK", style = UIAlertActionStyleDefault, handler = {
                    alert.dismissViewControllerAnimated(flag = true, completion = null)
                    onDismissRequest()
                })

            val actionCancel = UIAlertAction.actionWithTitle(
                "Cancel",
                style = UIAlertActionStyleCancel,
                handler = {
                    alert.dismissViewControllerAnimated(flag = true, completion = null)
                    onDismissRequest()
                })

            alert.addAction(action)
            alert.addAction(actionCancel)
            rootViewController?.presentViewController(alert, animated = true, completion = null)

            alert.view
        },
        update = {

        }
    )
}