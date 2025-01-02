@file:OptIn(ExperimentalMaterial3Api::class)

package org.rocketserverkmm.project.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.compose.viewmodel.koinViewModel
import org.rocketserverkmm.project.presentation.states.ButtonState
import org.rocketserverkmm.project.presentation.states.LoginAction
import org.rocketserverkmm.project.presentation.states.LoginDestination
import org.rocketserverkmm.project.presentation.viewmodels.LoginViewModel

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: LoginViewModel = koinViewModel<LoginViewModel>()

        val state by viewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        var email by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp)
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                text = state.titleText
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                label = { Text(state.labelText) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                value = email,
                onValueChange = { email = it }
            )

            Button(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .fillMaxWidth(),

                onClick = {
                    viewModel.actionToDestination(LoginAction.ClickSubmit(email))
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = when(state.buttonState) {
                        ButtonState.Error -> Color(0xFFFFABAB)
                        ButtonState.Loading -> MaterialTheme.colorScheme.primary
                        ButtonState.Success -> Color(0xFFA5D6A7)
                        null -> MaterialTheme.colorScheme.primary
                    }
                )
            ) {
                when(state.buttonState){
                    ButtonState.Loading -> LoadingCompose()
                    ButtonState.Success -> Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Success",
                        tint = Color.White
                    )
                    ButtonState.Error -> Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Error",
                        tint = Color.White
                    )
                    null -> Text(text = state.buttonText)
                }
            }

            LaunchedEffect(Unit) {
                viewModel.destination.collect { destination ->
                    when (destination) {
                        LoginDestination.GoBack -> navigator.pop()
                    }
                }
            }
        }
    }
}



@Composable
fun LoadingCompose() {
    CircularProgressIndicator(
        modifier = Modifier
            .size(24.dp),
        color = LocalContentColor.current,
        strokeWidth = 2.dp,
    )
}
