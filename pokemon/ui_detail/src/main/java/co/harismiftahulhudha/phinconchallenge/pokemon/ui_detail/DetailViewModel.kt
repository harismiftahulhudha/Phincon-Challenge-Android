package co.harismiftahulhudha.phinconchallenge.pokemon.ui_detail

import androidx.lifecycle.*
import co.harismiftahulhudha.phinconchallenge.core.util.result.Error
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.DetailModel
import co.harismiftahulhudha.phinconchallenge.pokemon.domain.model.PokemonDetailModel
import co.harismiftahulhudha.phinconchallenge.pokemon.interactor.usecase.GetPokemonDetailUseCase
import co.harismiftahulhudha.phinconchallenge.pokemon.interactor.usecase.ReleasePokemonUseCase
import co.harismiftahulhudha.phinconchallenge.pokemon.interactor.usecase.RequestCatchingPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    private val requestCatchingPokemonUseCase: RequestCatchingPokemonUseCase,
    private val releasePokemonUseCase: ReleasePokemonUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    private var id = state.get<Long>("id") ?: -1L
        set(value) {
            field = value
            state["id"] = value
        }

    var isCaught = state.get<Boolean>("isCaught") ?: false
        set(value) {
            field = value
            state["isCaught"] = value
        }

    var indexNickName = state.get<Int>("indexNickName") ?: -1
        set(value) {
            field = value
            state["indexNickName"] = value
        }

    var nickname = state.get<String>("nickname") ?: ""
        set(value) {
            field = value
            state["nickname"] = value
        }

    private val channel = Channel<Event>()
    val event = channel.receiveAsFlow()

    private val _detail: MutableLiveData<Pair<PokemonDetailModel, Triple<List<DetailModel>, List<DetailModel>, List<DetailModel>>>> = MediatorLiveData()
    val detail: LiveData<Pair<PokemonDetailModel, Triple<List<DetailModel>, List<DetailModel>, List<DetailModel>>>> = _detail

    fun getDetail() = viewModelScope.launch {
        getPokemonDetailUseCase(id).collect {
            it.fold(
                loading = { data ->
                    channel.send(Event.OnLoadingGetDetail)
                    data?.let {
                        isCaught = data.first.pokemon.isCaught
                        _detail.postValue(data)
                    }
                },
                success = { data, _ ->
                    isCaught = data.first.pokemon.isCaught
                    _detail.postValue(data)
                },
                failure = { error, data ->
                    data?.let {
                        isCaught = data.first.pokemon.isCaught
                        _detail.postValue(data)
                    }
                    channel.send(Event.OnErrorGetDetail(error))
                }
            )
        }
    }

    fun requestCatchPokemon() = viewModelScope.launch {
        requestCatchingPokemonUseCase().collect {
            it.fold(
                loading = {
                    channel.send(Event.OnLoading)
                },
                success = { _, _ ->
                    channel.send(Event.OnSuccessRequestCatchingPokemon(id))
                },
                failure = { error, _ ->
                    channel.send(Event.OnError(error))
                }
            )
        }
    }

    fun releasePokemon() = viewModelScope.launch {
        releasePokemonUseCase(id = id).collect {
            it.fold(
                loading = {
                    channel.send(Event.OnLoading)
                },
                success = { data, _ ->
                    isCaught = false
                    nickname = ""
                    indexNickName = -1
                    channel.send(Event.OnSuccessReleasePokemon(data))
                },
                failure = { error, _ ->
                    channel.send(Event.OnError(error))
                }
            )
        }
    }

    fun onSuccessRenamePokemon() = viewModelScope.launch {
        channel.send(Event.OnSuccessRenamePokemon(nickname))
    }

    fun onBackPreviousPage() = viewModelScope.launch {
        channel.send(Event.OnBackPreviousPage)
    }

    fun onClickAbout() = viewModelScope.launch {
        channel.send(Event.OnClickAbout)
    }

    fun onClickStat() = viewModelScope.launch {
        channel.send(Event.OnClickStat)
    }

    fun onClickMove() = viewModelScope.launch {
        channel.send(Event.OnClickMove)
    }

    fun onClickRelease() = viewModelScope.launch {
        channel.send(Event.OnClickRelease)
    }

    fun onClickCatch() = viewModelScope.launch {
        channel.send(Event.OnClickCatch)
    }

    fun onClickRename() = viewModelScope.launch {
        channel.send(Event.OnClickRename(id))
    }

    fun onSuccessCatchPokemon() = viewModelScope.launch {
        channel.send(Event.OnSuccessCatchPokemon)
    }

    sealed class Event {
        object OnBackPreviousPage: Event()
        object OnLoadingGetDetail: Event()
        data class OnErrorGetDetail(val error: Error): Event()
        object OnClickAbout: Event()
        object OnClickStat: Event()
        object OnClickMove: Event()
        object OnClickCatch: Event()
        object OnClickRelease: Event()
        data class OnClickRename(val id: Long): Event()
        data class OnSuccessRequestCatchingPokemon(val id: Long): Event()
        object OnSuccessCatchPokemon: Event()
        data class OnSuccessReleasePokemon(val data: PokemonDetailModel): Event()
        data class OnSuccessRenamePokemon(val nickname: String): Event()
        object OnLoading: Event()
        data class OnError(val error: Error): Event()
    }
}