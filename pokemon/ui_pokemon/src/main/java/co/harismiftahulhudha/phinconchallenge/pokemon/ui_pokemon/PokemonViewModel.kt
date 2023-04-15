package co.harismiftahulhudha.phinconchallenge.pokemon.ui_pokemon

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.PokemonModel
import co.harismiftahulhudha.phinconchallenge.pokemon.interactor.usecase.GetPokemonListUseCase
import co.harismiftahulhudha.phinconchallenge.pokemon.ui_shares.PokemonPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    private val channel = Channel<Event>()
    val event = channel.receiveAsFlow()

    val pokemon = Pager(
        config = PagingConfig(
            pageSize = BuildConfig.LIMIT.toInt(),
            enablePlaceholders = false
        )
    ) {
        PokemonPagingSource(
            isCaught = false, getPokemonListUseCase = getPokemonListUseCase
        )
    }.flow.cachedIn(viewModelScope)

    fun onClickItem(position: Int, data: PokemonModel) = viewModelScope.launch {
        channel.send(Event.OnClickItem(position, data))
    }

    sealed class Event {
        data class OnClickItem(val position: Int, val data: PokemonModel): Event()
    }
}