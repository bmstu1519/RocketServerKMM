package org.rocketserverkmm.project.presentation.states

sealed class ButtonState {
    data object Loading: ButtonState()
    data object Success: ButtonState()
    data object Error: ButtonState()
}

//sealed class SubmitButtonState{
//
//}