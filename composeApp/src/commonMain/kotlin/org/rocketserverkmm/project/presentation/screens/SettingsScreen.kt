package org.rocketserverkmm.project.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.compose.viewmodel.koinViewModel
import org.rocketserverkmm.project.platform.AlertDialog
import org.rocketserverkmm.project.presentation.states.SettingsAction
import org.rocketserverkmm.project.presentation.states.SettingsDestination
import org.rocketserverkmm.project.presentation.viewmodels.SettingsViewModel

class SettingsScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: SettingsViewModel = koinViewModel<SettingsViewModel>()
        val state by viewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        var showAlert by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp),
                    text = "Темная тема"
                )
                Switch(
                    checked = state.isDarkTheme,
                    onCheckedChange = { viewModel.actionToDestination(SettingsAction.ChangeTheme) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { viewModel.actionToDestination(SettingsAction.ClickAuthButton) },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Выйти из аккаунта")
            }
        }

        if(showAlert) {
            Box(modifier = Modifier.fillMaxSize()) {
                AlertDialog(
                    modifier = Modifier.fillMaxWidth().padding(4.dp).align(Alignment.Center),
                ) {
                    println("onDismissRequest")
//                                showDialog = false
                    showAlert = false
                }
            }
        }
        LaunchedEffect(Unit) {
            viewModel.destination.collect { destination ->
                when(destination) {
                    SettingsDestination.GoToLogin -> navigator.push(LoginScreen())
                    SettingsDestination.ShowAlert -> {
                        showAlert = true
                    }
                }
            }
        }
    }
}