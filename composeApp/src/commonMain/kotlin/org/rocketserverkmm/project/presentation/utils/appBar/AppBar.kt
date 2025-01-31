package org.rocketserverkmm.project.presentation.utils.appBar

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class AppBarState(
    val title: String = "RocketServerKMM",
    val showBackButton: Boolean = false,
    val iconAction: ImageVector = Icons.Default.Close,
    val onBackClick: () -> Unit = {}
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    appBarState: AppBarState
) {
    Surface(
        shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp),
        color = MaterialTheme.colorScheme.primary,
    ) {
        Surface(
            shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp),
            color = MaterialTheme.colorScheme.primary,
        ) {
            TopAppBar(
                title = { Text(appBarState.title) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    if (appBarState.showBackButton) {
                        IconButton(onClick = appBarState.onBackClick) {
                            Icon(
                                imageVector = appBarState.iconAction,
                                contentDescription = "Назад",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                },
            )
        }
    }
}

val LocalAppBarState = compositionLocalOf<MutableState<AppBarState>> {
    error("TopBarState not provided")
}