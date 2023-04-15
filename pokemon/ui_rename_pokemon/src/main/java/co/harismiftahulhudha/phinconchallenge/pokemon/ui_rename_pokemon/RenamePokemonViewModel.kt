package co.harismiftahulhudha.phinconchallenge.pokemon.ui_rename_pokemon

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.harismiftahulhudha.phinconchallenge.core.util.result.Error
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.PokemonDetailModel
import co.harismiftahulhudha.phinconchallenge.pokemon.interactor.usecase.CatchPokemonUseCase
import co.harismiftahulhudha.phinconchallenge.pokemon.interactor.usecase.RenamePokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RenamePokemonViewModel @Inject constructor(
    private val renamePokemonUseCase: RenamePokemonUseCase,
    private val catchPokemonUseCase: CatchPokemonUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    private var id = state.get<Long>("id") ?: -1L
        set(value) {
            field = value
            state["id"] = value
        }

    var isCatching = state.get<Boolean>("isCatching") ?: false
        set(value) {
            field = value
            state["isCatching"] = value
        }

    var nickname = state.get<String>("nickname") ?: ""
        set(value) {
            field = value
            state["nickname"] = value
        }

    private val channel = Channel<Event>()
    val event = channel.receiveAsFlow()

    fun renamePokemon() = viewModelScope.launch {
        renamePokemonUseCase(nickName = nickname, id = id).collect {
            it.fold(
                loading = {
                    channel.send(Event.OnLoading)
                },
                success = { data, _ ->
                    channel.send(Event.OnSuccess(data))
                },
                failure = { error, _ ->
                    channel.send(Event.OnError(error))
                }
            )
        }
    }

    fun catchPokemon() = viewModelScope.launch {
        catchPokemonUseCase(nickName = nickname, id = id).collect {
            it.fold(
                loading = {
                    channel.send(Event.OnLoading)
                },
                success = { data, _ ->
                    channel.send(Event.OnSuccess(data))
                },
                failure = { error, _ ->
                    channel.send(Event.OnError(error))
                }
            )
        }
    }

    fun onClickSave() = viewModelScope.launch {
        channel.send(Event.OnClickSave)
    }

    sealed class Event {
        object OnClickSave: Event()
        object OnLoading: Event()
        data class OnSuccess(val data: PokemonDetailModel): Event()
        data class OnError(val error: Error): Event()
    }
}