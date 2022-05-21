package io.github.sds100.keymapper.mappings.keymaps.detection

import io.github.sds100.keymapper.data.PreferenceDefaults

/**
 * Created by sds100 on 21/05/2022.
 */
data class DefaultKeyMapOptions(
    val longPressDelay: Long,
    val doublePressDelay: Long,
    val sequenceTriggerTimeout: Long,
    val vibrateDuration: Long,
    val forceVibrate: Boolean
) {
    companion object {
        val DEFAULT: DefaultKeyMapOptions = DefaultKeyMapOptions(
            PreferenceDefaults.LONG_PRESS_DELAY.toLong(),
            PreferenceDefaults.DOUBLE_PRESS_DELAY.toLong(),
            PreferenceDefaults.SEQUENCE_TRIGGER_TIMEOUT.toLong(),
            PreferenceDefaults.VIBRATION_DURATION.toLong(),
            PreferenceDefaults.FORCE_VIBRATE,
        )
    }
}