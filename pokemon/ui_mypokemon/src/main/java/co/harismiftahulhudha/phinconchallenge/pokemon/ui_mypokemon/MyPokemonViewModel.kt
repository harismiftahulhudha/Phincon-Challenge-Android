package co.harismiftahulhudha.phinconchallenge.pokemon.ui_mypokemon

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import co.harismiftahulhudha.phinconchallenge.core.util.result.Error
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.PokemonDetailModel
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.PokemonModel
import co.harismiftahulhudha.phinconchallenge.pokemon.interactor.usecase.GetPokemonListUseCase
import co.harismiftahulhudha.phinconchallenge.pokemon.interactor.usecase.ReleasePokemonUseCase
import co.harismiftahulhudha.phinconchallenge.pokemon.ui_shares.PokemonPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPokemonViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val releasePokemonUseCase: ReleasePokemonUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    var clickPosition = state.get<Int>("clickPosition") ?: -1
        set(value) {
            field = value
            state["clickPosition"] = value
        }

    private val channel = Channel<Event>()
    val event = channel.receiveAsFlow()

    val pokemon = Pager(
        config = PagingConfig(
            pageSize = BuildConfig.LIMIT.toInt(),
            enablePlaceholders = false
        )
    ) {
        PokemonPagingSource(
            isCaught = true, getPokemonListUseCase = getPokemonListUseCase
        )
    }.flow.cachedIn(viewModelScope)

    fun releasePokemon(id: Long) = viewModelScope.launch {
        releasePokemonUseCase(id).collect {
            it.fold(
                loading = {
                    channel.send(Event.OnLoadingReleasePokemon)
                },
                success = { data, _ ->
                    channel.send(Event.OnSuccessReleasePokemon(data))
                },
                failure = { error, data ->
                    channel.send(Event.OnErrorReleasePokemon(error))
                }
            )
        }
    }

    fun onClickItem(position: Int, data: PokemonModel) = viewModelScope.launch {
        channel.send(Event.OnClickItem(position, data))
    }

    fun onClickRelease(position: Int, data: PokemonModel) = viewModelScope.launch {
        channel.send(Event.OnClickRelease(position, data))
    }

    fun onClickRename(position: Int, data: PokemonModel) = viewModelScope.launch {
        clickPosition = position
        channel.send(Event.OnClickRename(position, data))
    }

    sealed class Event {
        data class OnClickItem(val position: Int, val data: PokemonModel): Event()
        data class OnClickRelease(val position: Int, val data: PokemonModel): Event()
        data class OnClickRename(val position: Int, val data: PokemonModel): Event()
        object OnLoadingReleasePokemon: Event()
        data class OnSuccessReleasePokemon(val data: PokemonDetailModel): Event()
        data class OnErrorReleasePokemon(val error: Error): Event()
    }
}