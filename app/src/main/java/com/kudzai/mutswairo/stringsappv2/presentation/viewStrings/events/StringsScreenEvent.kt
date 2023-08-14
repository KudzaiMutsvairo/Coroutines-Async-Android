package com.kudzai.mutswairo.stringsappv2.presentation.viewStrings.events

/**
 * Sealed class that represents the different events that can be triggered on the screen
 */
sealed class StringsScreenEvent {
    object OnGetStrings : StringsScreenEvent()
}
