package io.github.sds100.keymapper.system.intents

import android.content.Intent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import assertk.assertThat
import assertk.assertions.*
import io.github.sds100.keymapper.util.firstBlocking
import io.github.sds100.keymapper.util.ui.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.BeforeEach

/**
 * Created by sds100 on 29/04/2022.
 */
@ExperimentalCoroutinesApi
internal class ConfigIntentViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var fakeResourceProvider: FakeResourceProvider
    private lateinit var viewModel: ConfigIntentViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        fakeResourceProvider = FakeResourceProvider()
        viewModel = ConfigIntentViewModel(fakeResourceProvider)
    }

    @Test
    fun showFlagsDialog_whenNoFlags_checkNoFlags() {
        viewModel.showFlagsDialog()
        val popupEvent: ShowPopupEvent = viewModel.showPopup.firstBlocking()
        val multipleChoiceDialog = popupEvent.ui as PopupUi.MultiChoice<*>

        assertThat(multipleChoiceDialog.items).each {
            it.prop(MultiChoiceItem<*>::isChecked).isFalse()
        }
    }

    @Test
    fun showFlagsDialog_whenFlags_checkFlags() {
        viewModel.showFlagsDialog()
        val addFlagPopupEvent: ShowPopupEvent = viewModel.showPopup.firstBlocking()
        viewModel.onUserResponse(addFlagPopupEvent.key, listOf(Intent.FLAG_ACTIVITY_NEW_TASK))

        viewModel.showFlagsDialog()
        val popupEvent: ShowPopupEvent = viewModel.showPopup.firstBlocking()
        val multipleChoiceDialog = popupEvent.ui as PopupUi.MultiChoice<*>
        val expectedCheckedItem = MultiChoiceItem(Intent.FLAG_ACTIVITY_NEW_TASK, "FLAG_ACTIVITY_NEW_TASK", true)

        assertThat(multipleChoiceDialog.items).any {
            it.isEqualTo(expectedCheckedItem)
        }
    }
}