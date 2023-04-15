package co.harismiftahulhudha.phinconchallenge.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoreViewModel@Inject constructor(
) : ViewModel() {

    private val channel = Channel<MainEvent>()
    val event = channel.receiveAsFlow()

    fun onShowMenu(isShow: Boolean) = viewModelScope.launch {
        channel.send(MainEvent.OnShowMenu(isShow))
    }

    sealed class MainEvent {
        data class OnShowMenu(val isShow: Boolean) : MainEvent()
    }
}