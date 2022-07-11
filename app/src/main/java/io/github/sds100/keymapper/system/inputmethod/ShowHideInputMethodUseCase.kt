package io.github.sds100.keymapper.system.inputmethod

import io.github.sds100.keymapper.system.accessibility.AccessibilityServiceAdapter
import io.github.sds100.keymapper.util.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

/**
 * Created by sds100 on 16/04/2021.
 */

class ShowHideInputMethodUseCaseImpl @Inject constructor(
    private val serviceAdapter: AccessibilityServiceAdapter
) : ShowHideInputMethodUseCase {
    override val onHiddenChange: Flow<Boolean> = serviceAdapter.eventReceiver.mapNotNull { event ->
        when (event) {
            Event.OnHideKeyboardEvent -> true
            Event.OnShowKeyboardEvent -> false
            else -> null
        }
    }

    override fun show() {
        runBlocking { serviceAdapter.send(Event.ShowKeyboard) }
    }

    override fun hide() {
        runBlocking { serviceAdapter.send(Event.HideKeyboard) }
    }
}

interface ShowHideInputMethodUseCase {
    val onHiddenChange: Flow<Boolean>
    fun show()
    fun hide()
}